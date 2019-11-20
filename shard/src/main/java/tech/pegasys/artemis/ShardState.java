package tech.pegasys.artemis;

import com.google.common.primitives.UnsignedLong;
import org.apache.tuweni.bytes.Bytes32;

public class ShardState {
  private UnsignedLong slot;
  private UnsignedLong gasprice;
  private Bytes32 data;
  private Bytes32 latest_block_root;

  public ShardState() {
  }

  public ShardState(UnsignedLong slot, UnsignedLong gasprice, Bytes32 data, Bytes32 latest_block_root) {
    this.slot = slot;
    this.gasprice = gasprice;
    this.data = data;
    this.latest_block_root = latest_block_root;
  }

  public UnsignedLong getSlot() {
    return slot;
  }

  public void setSlot(UnsignedLong slot) {
    this.slot = slot;
  }

  public UnsignedLong getGasprice() {
    return gasprice;
  }

  public void setGasprice(UnsignedLong gasprice) {
    this.gasprice = gasprice;
  }

  public Bytes32 getData() {
    return data;
  }

  public void setData(Bytes32 data) {
    this.data = data;
  }

  public Bytes32 getLatest_block_root() {
    return latest_block_root;
  }

  public void setLatest_block_root(Bytes32 latest_block_root) {
    this.latest_block_root = latest_block_root;
  }
}
