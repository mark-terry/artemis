package tech.pegasys.artemis;

import com.google.common.primitives.UnsignedLong;
import tech.pegasys.artemis.util.SSZTypes.SSZList;
import tech.pegasys.artemis.util.bls.BLSPublicKey;
import tech.pegasys.artemis.util.config.Constants;

import java.util.List;

import static tech.pegasys.artemis.util.config.Constants.MAX_VALIDATORS_PER_COMMITTEE;

public class CompactCommittee {
  private SSZList<BLSPublicKey> pubkeys;
  private SSZList<Integer> compact_validators;

  public CompactCommittee() {
    pubkeys = new SSZList<BLSPublicKey>(BLSPublicKey.class , MAX_VALIDATORS_PER_COMMITTEE);
    compact_validators = new SSZList<Integer>(Integer.class, MAX_VALIDATORS_PER_COMMITTEE);
  }

  public CompactCommittee(SSZList<BLSPublicKey> pubkeys, SSZList<Integer> compact_validators) {
    this.pubkeys = pubkeys;
    this.compact_validators = compact_validators;
  }

  public CompactCommittee(List<BLSPublicKey> pubkeys, List<Integer> compact_validators) {
    this.pubkeys = new SSZList<BLSPublicKey>(pubkeys, MAX_VALIDATORS_PER_COMMITTEE, BLSPublicKey.class);
    this.compact_validators = new SSZList<Integer>(compact_validators, MAX_VALIDATORS_PER_COMMITTEE, Integer.class);
  }
  public SSZList<BLSPublicKey> getPubkeys() {
    return pubkeys;
  }

  public void setPubkeys(SSZList<BLSPublicKey> pubkeys) {
    this.pubkeys = pubkeys;
  }

  public SSZList<Integer> getCompact_validators() {
    return compact_validators;
  }

  public void setCompact_validators(SSZList<Integer> compact_validators) {
    this.compact_validators = compact_validators;
  }
}
