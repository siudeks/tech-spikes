# PySpark ORC Examples

A demonstration of working with Apache ORC (Optimized Row Columnar) files using PySpark.

## Overview

This project demonstrates:
- Creating and writing data to ORC format
- Reading ORC files and performing aggregations
- Saving aggregation results back to ORC

## Project Structure

```
pyspark-orc-examples/
├── README.md                    # Documentation
├── sum_orc_data.ipynb          # Main notebook with examples
└── result/                      # Output directory for aggregated data
```

## Getting Started

**Prerequisites**: Python 3.7+, PySpark, Jupyter Notebook

**Run the notebook**:
```bash
jupyter notebook sum_orc_data.ipynb
```

## Example Output

**Original Data**:
```
+----+----+
|dim1|dim2|
+----+----+
|   a|   1|
|   b|   2|
+----+----+
```

**Aggregation Result**:
```
+---------+
|sum(dim2)|
+---------+
|        3|
+---------+
```

## Key Code

```python
# Create and write ORC data
from pyspark.sql import SparkSession
from pyspark.sql.types import StructType, StructField, StringType, IntegerType

spark = SparkSession.builder.appName("ORC Example").master("local[4]").getOrCreate()
data = [("a", 1), ("b", 2)]
schema = StructType([
    StructField("dim1", StringType(), True),
    StructField("dim2", IntegerType(), True)
])
df = spark.createDataFrame(data, schema)
df.write.mode("overwrite").orc("/tmp/example.orc")

# Read and aggregate
df_orc = spark.read.orc("/tmp/example.orc")
agg_df = df_orc.agg({"dim2": "sum"})
agg_df.write.mode("overwrite").orc("result/")
```

## Why ORC?

- **High compression** for storage efficiency
- **Fast query performance** for analytics
- **Schema evolution** support
- **Built-in statistics** for optimization
