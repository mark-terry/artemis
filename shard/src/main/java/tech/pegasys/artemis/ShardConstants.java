package tech.pegasys.artemis;

import com.google.common.primitives.UnsignedLong;
import org.apache.tuweni.bytes.Bytes;
import tech.pegasys.artemis.util.SSZTypes.Bytes4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShardConstants {
  static final long MAX_SHARD_BLOCKS_PER_ATTESTATION = 12;
  static final UnsignedLong SHARD_COMMITTEE_PERIOD = UnsignedLong.valueOf(256);
  static final Bytes4 DOMAIN_SHARD_COMMITTEE = new Bytes4(Bytes.of(192));
  static final int ACTIVE_SHARDS = 64;
  static final UnsignedLong LIGHT_CLIENT_COMMITTEE_PERIOD = UnsignedLong.valueOf(256);
  static final Bytes4 DOMAIN_LIGHT_CLIENT = new Bytes4(Bytes.of(192));
  final static int TARGET_SHARD_BLOCK_SIZE = 262144;
  static final int GASPRICE_ADJUSTMENT_COEFFICIENT = 8;
  static final UnsignedLong MIN_GASPRICE = UnsignedLong.valueOf(32);
  static final UnsignedLong MAX_GASPRICE = UnsignedLong.valueOf(16384);


  static final List<Integer> SHARD_BLOCK_OFFSETS = Arrays.asList(1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233);

}
