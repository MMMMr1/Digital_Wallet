/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.michalenok.wallet.kafka.schema;
@org.apache.avro.specific.AvroGenerated
public enum TransactionStatus implements org.apache.avro.generic.GenericEnumSymbol<TransactionStatus> {
  SUCCESSFUL, UNSUCCESSFUL  ;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"TransactionStatus\",\"namespace\":\"com.michalenok.wallet.kafka.schema\",\"symbols\":[\"SUCCESSFUL\",\"UNSUCCESSFUL\"]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
}
