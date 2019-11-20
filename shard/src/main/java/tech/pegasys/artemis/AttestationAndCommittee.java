package tech.pegasys.artemis;

import com.google.common.primitives.UnsignedLong;
import tech.pegasys.artemis.datastructures.operations.Attestation;

import java.util.List;

public class AttestationAndCommittee {
  private List<Integer> committee;
  private Attestation attestation;

  public AttestationAndCommittee() {
  }

  public AttestationAndCommittee(List<Integer> committee, Attestation attestation) {
    this.committee = committee;
    this.attestation = attestation;
  }

  public List<Integer> getCommittee() {
    return committee;
  }

  public void setCommittee(List<Integer> committee) {
    this.committee = committee;
  }

  public Attestation getAttestation() {
    return attestation;
  }

  public void setAttestation(Attestation attestation) {
    this.attestation = attestation;
  }
}
