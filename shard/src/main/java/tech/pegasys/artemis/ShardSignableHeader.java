package tech.pegasys.artemis;

import com.google.common.primitives.UnsignedLong;
import org.apache.tuweni.bytes.Bytes32;

public class ShardSignableHeader {
  private Bytes32 shard_parent_root;
  private Bytes32 beacon_parent_root;
  private UnsignedLong slot;
  private Bytes32 body_root;

  public ShardSignableHeader() {
  }

  public ShardSignableHeader(Bytes32 shard_parent_root, Bytes32 beacon_parent_root, UnsignedLong slot, Bytes32 body_root) {
    this.shard_parent_root = shard_parent_root;
    this.beacon_parent_root = beacon_parent_root;
    this.slot = slot;
    this.body_root = body_root;
  }

  public Bytes32 getShard_parent_root() {
    return shard_parent_root;
  }

  public void setShard_parent_root(Bytes32 shard_parent_root) {
    this.shard_parent_root = shard_parent_root;
  }

  public Bytes32 getBeacon_parent_root() {
    return beacon_parent_root;
  }

  public void setBeacon_parent_root(Bytes32 beacon_parent_root) {
    this.beacon_parent_root = beacon_parent_root;
  }

  public UnsignedLong getSlot() {
    return slot;
  }

  public void setSlot(UnsignedLong slot) {
    this.slot = slot;
  }

  public Bytes32 getBody_root() {
    return body_root;
  }

  public void setBody_root(Bytes32 body_root) {
    this.body_root = body_root;
  }
}
