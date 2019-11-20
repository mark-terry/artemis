package tech.pegasys.artemis;

import com.google.common.primitives.UnsignedLong;

public class Shard {
  UnsignedLong index;

  public Shard(UnsignedLong index) {
    this.index = index;
  }

  public UnsignedLong getIndex() {
    return index;
  }

  public void setIndex(UnsignedLong index) {
    this.index = index;
  }
}
