package tech.pegasys.artemis;

import com.google.common.primitives.UnsignedLong;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import tech.pegasys.artemis.datastructures.operations.Attestation;
import tech.pegasys.artemis.datastructures.state.BeaconState;
import tech.pegasys.artemis.datastructures.state.Validator;
import tech.pegasys.artemis.datastructures.util.CommitteeUtil;
import tech.pegasys.artemis.util.bls.BLSPublicKey;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static tech.pegasys.artemis.ShardConstants.ACTIVE_SHARDS;
import static tech.pegasys.artemis.ShardConstants.DOMAIN_LIGHT_CLIENT;
import static tech.pegasys.artemis.ShardConstants.DOMAIN_SHARD_COMMITTEE;
import static tech.pegasys.artemis.ShardConstants.GASPRICE_ADJUSTMENT_COEFFICIENT;
import static tech.pegasys.artemis.ShardConstants.LIGHT_CLIENT_COMMITTEE_PERIOD;
import static tech.pegasys.artemis.ShardConstants.MAX_GASPRICE;
import static tech.pegasys.artemis.ShardConstants.MIN_GASPRICE;
import static tech.pegasys.artemis.ShardConstants.SHARD_BLOCK_OFFSETS;
import static tech.pegasys.artemis.ShardConstants.SHARD_COMMITTEE_PERIOD;
import static tech.pegasys.artemis.ShardConstants.TARGET_SHARD_BLOCK_SIZE;
import static tech.pegasys.artemis.datastructures.util.BeaconStateUtil.bytes_to_int;
import static tech.pegasys.artemis.datastructures.util.BeaconStateUtil.compute_epoch_at_slot;
import static tech.pegasys.artemis.datastructures.util.BeaconStateUtil.get_current_epoch;
import static tech.pegasys.artemis.datastructures.util.BeaconStateUtil.get_seed;
import static tech.pegasys.artemis.datastructures.util.BeaconStateUtil.max;
import static tech.pegasys.artemis.datastructures.util.BeaconStateUtil.min;
import static tech.pegasys.artemis.datastructures.util.CommitteeUtil.get_beacon_committee;
import static tech.pegasys.artemis.datastructures.util.ValidatorsUtil.get_active_validator_indices;
import static tech.pegasys.artemis.util.config.Constants.EFFECTIVE_BALANCE_INCREMENT;
import static tech.pegasys.artemis.util.config.Constants.TARGET_COMMITTEE_SIZE;

public class ShardHelper {

  public static List<Integer> get_online_indices(BeaconState state){
    List<Integer> active_validators = get_active_validator_indices(state, get_current_epoch(state));
    return active_validators.stream().filter(i -> !state.getOnline_countdown().get(i).equals(0)).collect(Collectors.toList());
  }

  public static int pack_compact_validator(int index, boolean slashed, int balance_in_increments){
    //Creates a compact validator object representing index, slashed status, and compressed balance.
    //Takes as input balance-in-increments (// EFFECTIVE_BALANCE_INCREMENT) to preserve symmetry with
    //the unpacking function.
    return (index << 16) + ((slashed? 1 : 0) << 15) + balance_in_increments;
  }

  public static CompactCommittee committee_to_compact_committee(BeaconState state, List<Integer> committee){
    List<Validator> validators = committee.stream().map(index -> state.getValidators().get(index)).collect(Collectors.toList());
    Map<Integer, Validator> map = zip(committee, validators);
    List<Integer> compact_validators = map.entrySet().stream()
            .map(item -> pack_compact_validator(item.getKey().intValue(), map.get(item.getValue()).isSlashed(), map.get(item.getValue()).getEffective_balance().dividedBy(UnsignedLong.valueOf(EFFECTIVE_BALANCE_INCREMENT)).intValue()))
            .collect(Collectors.toList());
    List<BLSPublicKey> pubkeys = validators.stream().map(validator -> validator.getPubkey()).collect(Collectors.toList());
    return new CompactCommittee(pubkeys, compact_validators);
  }

  public static List<Integer> get_shard_committee(BeaconState beacon_state, UnsignedLong epoch, Integer shard){
    UnsignedLong source_epoch = epoch.minus(epoch.mod(SHARD_COMMITTEE_PERIOD));
    if(source_epoch.compareTo(UnsignedLong.ZERO) > 0){
      source_epoch = source_epoch.minus(SHARD_COMMITTEE_PERIOD);
    }
    List<Integer> active_validator_indices = get_active_validator_indices(beacon_state, source_epoch);
    Bytes32 seed = get_seed(beacon_state, source_epoch, DOMAIN_SHARD_COMMITTEE);
    return CommitteeUtil.compute_committee(active_validator_indices, seed, 0, ACTIVE_SHARDS);
  }

  public static int get_shard_proposer_index(BeaconState beacon_state, UnsignedLong slot, Integer shard){
    List<Integer> committee = get_shard_committee(beacon_state, compute_epoch_at_slot(slot), shard);
    byte[] arr = get_seed(beacon_state, get_current_epoch(beacon_state), DOMAIN_SHARD_COMMITTEE).toArray();
    long r = bytes_to_int(Bytes.of(Arrays.copyOfRange(arr, 0, 9)));
    return committee.get((int)r/committee.size());
  }

  public static List<Integer> get_light_client_committee(BeaconState beacon_state, UnsignedLong epoch){
    UnsignedLong source_epoch = epoch.minus(epoch.mod(LIGHT_CLIENT_COMMITTEE_PERIOD));
    if(source_epoch.compareTo(UnsignedLong.ZERO) > 0){
      source_epoch = source_epoch.minus(LIGHT_CLIENT_COMMITTEE_PERIOD);
    }
    List<Integer> active_validator_indices = get_active_validator_indices(beacon_state, source_epoch);
    Bytes32 seed = get_seed(beacon_state, source_epoch, DOMAIN_LIGHT_CLIENT);
    return CommitteeUtil.compute_committee(active_validator_indices, seed, 0, ACTIVE_SHARDS).subList(0, TARGET_COMMITTEE_SIZE);
  }

  public static AttestationAndCommittee get_indexed_attestation(BeaconState beacon_state, Attestation attestation){
    List<Integer> committee = get_beacon_committee(beacon_state, attestation.getData().getSlot(), attestation.getData().getIndex());
    return new AttestationAndCommittee(committee, attestation);
  }

  public static UnsignedLong get_updated_gasprice(UnsignedLong prev_gasprice, int length){
    if(length > TARGET_SHARD_BLOCK_SIZE){
      UnsignedLong delta = prev_gasprice
              .times(UnsignedLong.valueOf(length - TARGET_SHARD_BLOCK_SIZE))
              .dividedBy(UnsignedLong.valueOf(TARGET_SHARD_BLOCK_SIZE))
              .dividedBy(UnsignedLong.valueOf(GASPRICE_ADJUSTMENT_COEFFICIENT));
      return min(prev_gasprice.plus(delta), MAX_GASPRICE);
    }
    UnsignedLong delta = prev_gasprice
            .times(UnsignedLong.valueOf(length - TARGET_SHARD_BLOCK_SIZE))
            .dividedBy(UnsignedLong.valueOf(TARGET_SHARD_BLOCK_SIZE))
            .dividedBy(UnsignedLong.valueOf(GASPRICE_ADJUSTMENT_COEFFICIENT));
    return max(prev_gasprice, MIN_GASPRICE.plus(delta)).minus(delta);
  }

  public static Shard get_start_shard(BeaconState state, UnsignedLong slot){
    //TODO: implement start shard logic
    return new Shard(UnsignedLong.ZERO);
  }

  public static Shard get_shard(BeaconState state, Attestation attestation){
    return new Shard((attestation.getData().getIndex().plus(get_start_shard(state, attestation.getData().getSlot()).getIndex())).mod(UnsignedLong.valueOf(ACTIVE_SHARDS)));
  }

  public static List<Integer> get_offset_slots(BeaconState state, UnsignedLong start_slot){
    return SHARD_BLOCK_OFFSETS.stream()
            .filter(x -> !(start_slot.plus(UnsignedLong.valueOf(x)).compareTo(state.getSlot()) < 0))
            .map(x -> start_slot.plus(UnsignedLong.valueOf(x)).intValue()).collect(Collectors.toList());
  }

  public static <K, V> Map<K, V> zip(List<K> keys, List<V> values) {
    return IntStream.range(0, keys.size()).boxed()
            .collect(Collectors.toMap(keys::get, values::get));
  }
}
