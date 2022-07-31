# ASCII Table

**A lightweight ASCII table generator** 

## About

An API to easily generate CLI tables. I created this library to save time when working on CS class assignements, it is also a great tool for debugging.

## How To Use

### **FastTable**

A FastTable is a fast way to create a table. It is optimized and will not resize automatically. The cell width will be calculated according to the longest `String` in the header. 

### Sample Code

``` Java
		// Initialize the table with the number of columns.
		FastTable table = new FastTable(3);

		// Call addHeader and pass a String array. The header is left
		// aligned by default. Use overloaded constructor to right align.
		table.addHeader(new String[] { "NAME", "LAST NAME", "AGE" });

		// Add one row at a time.
		table.addRow(new String[] { "John", "Danky", "23" });
		table.addRow(new String[] { "Peter",
				"The cell will add padding vertically to accomodate according to the column width", "23" });
		table.addRow(new String[] { "James", "Rondonon", "43" });

		// Use the overloaded constructor to add left aligned.
		table.addRow(new String[] { "Spunky", "McDonomonk", "34" }, true);
		
		table.addRow(new String[] { "The table adjusts the cell height automatically", "Trump",
				"12" });
		table.print();
	
```

### Sample Output

``` 
•-----------+-----------+-----------•
| NAME      | LAST NAME | AGE       |
•-----------+-----------+-----------•
|      John |     Danky |        23 |
•-----------+-----------+-----------•
|     Peter |  The cell |        23 |
|           | will add  |           |
|           | padding v |           |
|           | ertically |           |
|           | to accomo |           |
|           | date acco |           |
|           | rding to  |           |
|           | the colum |           |
|           | n width   |           |
•-----------+-----------+-----------•
|     James |  Rondonon |        43 |
•-----------+-----------+-----------•
| Spunky    | McDonomon | 34        |
|           | k         |           |
•-----------+-----------+-----------•
| The table |     Trump |        12 |
| adjusts t |           |           |
| he cell h |           |           |
| eight aut |           |           |
| omaticall |           |           |
| y         |           |           |
•-----------+-----------+-----------•
```

### Sample Code With Lambda

``` Java
		// Initialize the table with the number of columns.
		FastTable table = new FastTable(7);

		// Add the header.
		String[] h = { "cell1", "cell2", "cell3", "cell4", "cell5", "cell6", "cell7" };

		table.addHeader(h);

		// Some data we want to manipulate.
		int[] intArray = { 32, 453, 86, 23, 56, 23, 90 };

		// fillRow takes a callback the returned string from this callback function will
		// be added to the indexed cell.
		table.fillRow((i) -> {
			return Integer.toString(intArray[i]);
		});

		table.fillRow((i) -> {
			return Integer.toString(intArray[i] * 34);
		});

		table.fillRow((i) -> {
			if (intArray[i] % 2 == 0)
				return Integer.toString(intArray[i] / 2);
			else
				return Integer.toString(intArray[i]);
		});

		// Print the table and then add more rows.
		table.print();
		System.out.println();
		System.out.println();

		table.fillRow((i) -> {
			if (intArray[i] % 2 == 0)
				return Integer.toString(intArray[i] * 23);
			else
				return Integer.toString(intArray[i] - 32);
		});

		// Print the table.
		table.print();
```

### Sample Output With Lambda

``` 
•----------+----------+----------+----------+----------+----------+----------•
| cell1    | cell2    | cell3    | cell4    | cell5    | cell6    | cell7    |
•----------+----------+----------+----------+----------+----------+----------•
|       32 |      453 |       86 |       23 |       56 |       23 |       90 |
•----------+----------+----------+----------+----------+----------+----------•
|     1088 |    15402 |     2924 |      782 |     1904 |      782 |     3060 |
•----------+----------+----------+----------+----------+----------+----------•
|       16 |      453 |       43 |       23 |       28 |       23 |       45 |
•----------+----------+----------+----------+----------+----------+----------•



•----------+----------+----------+----------+----------+----------+----------•
| cell1    | cell2    | cell3    | cell4    | cell5    | cell6    | cell7    |
•----------+----------+----------+----------+----------+----------+----------•
|       32 |      453 |       86 |       23 |       56 |       23 |       90 |
•----------+----------+----------+----------+----------+----------+----------•
|     1088 |    15402 |     2924 |      782 |     1904 |      782 |     3060 |
•----------+----------+----------+----------+----------+----------+----------•
|       16 |      453 |       43 |       23 |       28 |       23 |       45 |
•----------+----------+----------+----------+----------+----------+----------•
|      736 |      421 |     1978 |       -9 |     1288 |       -9 |     2070 |
•----------+----------+----------+----------+----------+----------+----------•

```

## The `Table` API is currently uninplemented.
