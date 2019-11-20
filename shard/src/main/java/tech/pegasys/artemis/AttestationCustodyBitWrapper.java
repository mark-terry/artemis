package tech.pegasys.artemis;

import com.google.common.primitives.UnsignedLong;
import org.apache.tuweni.bytes.Bytes32;

public class AttestationCustodyBitWrapper {
  private Bytes32 attestation_root;
  private UnsignedLong block_index;
  private boolean bit;

  public AttestationCustodyBitWrapper() {
  }

  public AttestationCustodyBitWrapper(Bytes32 attestation_root, UnsignedLong block_index, boolean bit) {
    this.attestation_root = attestation_root;
    this.block_index = block_index;
    this.bit = bit;
  }

  public Bytes32 getAttestation_root() {
    return attestation_root;
  }

  public void setAttestation_root(Bytes32 attestation_root) {
    this.attestation_root = attestation_root;
  }

  public UnsignedLong getBlock_index() {
    return block_index;
  }

  public void setBlock_index(UnsignedLong block_index) {
    this.block_index = block_index;
  }

  public boolean isBit() {
    return bit;
  }

  public void setBit(boolean bit) {
    this.bit = bit;
  }
}
