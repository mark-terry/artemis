package tech.pegasys.artemis;

import com.google.common.primitives.UnsignedLong;
import org.apache.tuweni.bytes.Bytes32;
import tech.pegasys.artemis.util.SSZTypes.SSZList;
import tech.pegasys.artemis.util.bls.BLSSignature;

import java.util.ArrayList;

public class ShardTransition {

  private UnsignedLong slot;
  private SSZList<UnsignedLong> shard_block_lengths;
  private SSZList<SSZList<Bytes32>> shard_data_roots;
  private SSZList<ShardState> shard_states;
  private BLSSignature proposer_signature_aggregate;

  public ShardTransition() {
  }

  public ShardTransition(UnsignedLong slot, SSZList<UnsignedLong> shard_block_lengths, SSZList<SSZList<Bytes32>> shard_data_roots, SSZList<ShardState> shard_states, BLSSignature proposer_signature_aggregate) {
    this.slot = slot;
    this.shard_block_lengths = shard_block_lengths;
    this.shard_data_roots = shard_data_roots;
    this.shard_states = shard_states;
    this.proposer_signature_aggregate = proposer_signature_aggregate;
  }

  public UnsignedLong getSlot() {
    return slot;
  }

  public void setSlot(UnsignedLong slot) {
    this.slot = slot;
  }

  public SSZList<UnsignedLong> getShard_block_lengths() {
    return shard_block_lengths;
  }

  public void setShard_block_lengths(SSZList<UnsignedLong> shard_block_lengths) {
    this.shard_block_lengths = shard_block_lengths;
  }

  public SSZList<SSZList<Bytes32>> getShard_data_roots() {
    return shard_data_roots;
  }

  public void setShard_data_roots(SSZList<SSZList<Bytes32>> shard_data_roots) {
    this.shard_data_roots = shard_data_roots;
  }

  public SSZList<ShardState> getShard_states() {
    return shard_states;
  }

  public void setShard_states(SSZList<ShardState> shard_states) {
    this.shard_states = shard_states;
  }

  public BLSSignature getProposer_signature_aggregate() {
    return proposer_signature_aggregate;
  }

  public void setProposer_signature_aggregate(BLSSignature proposer_signature_aggregate) {
    this.proposer_signature_aggregate = proposer_signature_aggregate;
  }
}
