+++
title = "Bitronix Transaction"
weight = 5
+++

## background

Apache ShardingSphere provides XA transactions that integrate with the Bitronix implementation.

## Prerequisites

Introducing Maven dependency

```xml
<properties>
    <btm.version>2.1.3</btm.version>
</properties>

<dependency>
    <groupId>io.github.greycode</groupId>
    <artifactId>shardingsphere-jdbc-core</artifactId>
    <version>${shardingsphere.version}</version>
</dependency>

<!-- This module is required when using XA transactions -->
<dependency>
    <groupId>io.github.greycode</groupId>
    <artifactId>shardingsphere-transaction-xa-core</artifactId>
    <version>${shardingsphere.version}</version>
</dependency>

<dependency>
    <groupId>io.github.greycode</groupId>
    <artifactId>shardingsphere-transaction-xa-bitronix</artifactId>
    <version>${shardingsphere.version}</version>
</dependency>

<dependency>
    <groupId>org.codehaus.btm</groupId>
    <artifactId>btm</artifactId>
    <version>${btm.version}</version>
</dependency>
```

## Procedure

1. Configure the XA transaction type
2. Configure Bitronix

## Sample

### Configure the XA transaction type

Yaml:

```yaml
- !TRANSACTION
  defaultType: XA
  providerType: Bitronix
```

### Configure Bitronix (Deletable)
See [Bitronix's Official Documentation](https://github.com/bitronix/btm/wiki) for more details.
