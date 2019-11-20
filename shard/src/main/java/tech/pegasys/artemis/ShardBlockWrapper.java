/*
 * Copyright 2019 ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package tech.pegasys.artemis;

import com.google.common.primitives.UnsignedLong;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.Bytes32;
import tech.pegasys.artemis.util.SSZTypes.SSZVector;
import tech.pegasys.artemis.util.bls.BLSSignature;

public class ShardBlockWrapper {
  private Bytes32 shard_parent_root;
  private Bytes32 beacon_parent_root;
  private UnsignedLong slot;
  private SSZVector<Bytes> body;
  private BLSSignature signature;

  public ShardBlockWrapper() {
  }

  public ShardBlockWrapper(Bytes32 shard_parent_root, Bytes32 beacon_parent_root, UnsignedLong slot, SSZVector<Bytes> body, BLSSignature signature) {
    this.shard_parent_root = shard_parent_root;
    this.beacon_parent_root = beacon_parent_root;
    this.slot = slot;
    this.body = body;
    this.signature = signature;
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

  public SSZVector<Bytes> getBody() {
    return body;
  }

  public void setBody(SSZVector<Bytes> body) {
    this.body = body;
  }

  public BLSSignature getSignature() {
    return signature;
  }

  public void setSignature(BLSSignature signature) {
    this.signature = signature;
  }
}
