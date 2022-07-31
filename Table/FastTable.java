package Table;

import java.util.Arrays;
import java.util.function.Function;

/**
 * An API to easily generate CLI tables. I created this library to save time
 * when working on CS class assignements, it is also a great tool for debugging.
 * No tricky syntax, no clever notation, just a simple table making library.
 * 
 * @author jorgejimenez
 *
 */
public class FastTable {

	// This seems weird, but I might want to do some
	// type of customization on the type of character used.
	// Default
	private final char BORDER1 = '-';
	private final char CORNER1 = '•';
	private final char CONNECTOR1 = '+';

	// Current
	private char border_vertical = BORDER1;
	private char corner = CORNER1;
	private char connector = CONNECTOR1;

	// Where the table is created.
	private StringBuilder sb = new StringBuilder();
	private StringBuilder temp = new StringBuilder();

	// Table dimensions.
	private int colSize = 8;
	private int numCols;

	// Some constants and flags.
	private final char NEW_LINE = '\n';
	private boolean firstRow = true;
	private boolean lastRow = false;
	private boolean neePadding = false;
	private boolean hasHeader = false;

	/**
	 * <p>
	 * Construct a table with a fixed width automatically calculated when adding the
	 * header.
	 * </p>
	 * 
	 * @param numberOfColumns - the number of columns.
	 */
	public FastTable(int numberOfColumns) {
		this.numCols = numberOfColumns;
	}

	/**
	 * <p>
	 * Add a header row to the table. The header will be left aligned by default.
	 * </p>
	 * 
	 * @param headers - an {@code Array} of {@code String}s that will make up the
	 *                cells of the header.
	 */
	public void addHeader(String[] headers) {
		addHeader(headers, false);
	}

	/**
	 * <p>
	 * Add a header row to the table.
	 * </p>
	 * 
	 * @param headers     - an {@code Array} of {@code String}s that will make up
	 *                    the cells of the header.
	 * @param leftAligned - if true the header will be left aligned otherwise it
	 *                    will default to right aligned.
	 */
	public void addHeader(String[] headers, boolean rightAligned) {
		if (hasHeader)
			return;

		for (String el : headers) {
			int elSize = el.length();
			if (elSize > colSize)
				colSize = elSize;
		}
		hasHeader = true;
		// addRow(headers, leftAligned = confusing af
		addRow(headers, !rightAligned);
	}

	/**
	 * <p>
	 * Adds a row to the end of the table. The height of each cell will be
	 * automatically resized to fit the content according to the longest cell width
	 * of the header.
	 * </p>
	 * 
	 * @param elements    - an {@code Array} of {@code String}s containing the cells
	 *                    of the row.
	 * @param leftAligned - if true the row will be left aligned otherwise it will
	 *                    default to right aligned.
	 */
	public void addRow(String[] elements, boolean leftAligned) {

		if (!neePadding) {
			addCellBorder();
		}

		neePadding = false;

		boolean first = true;

		String[] padding = null;
		for (int i = 0; i < elements.length; i++) {

			int elSize = elements[i].length();

			if (elSize > colSize) {
				if (padding == null) {
					padding = new String[numCols];
					Arrays.fill(padding, " ");
					neePadding = true;
				}
				first = addCellString(elements[i].substring(0, colSize).trim(), first, leftAligned);
				padding[i] = elements[i].substring(colSize).trim();
			} else {
				first = addCellString(elements[i], first, leftAligned);
			}

		}

		sb.append(NEW_LINE);

		if (padding != null)
			addRow(padding, true);

		temp = new StringBuilder();
		temp.append(sb);
	}

	/**
	 * <p>
	 * Adds a row to the end of the table. The height of each cell will be
	 * automatically resized to fit the content according to the longest cell width
	 * of the header.
	 * </p>
	 * 
	 * @param elements - an {@code Array} of {@code String}s containing the cells of
	 *                 the row.
	 */
	public void addRow(String[] elements) {
		addRow(elements, false);
	}

	private boolean addCellString(String el, boolean first, boolean left) {
		sb.append(String.format((first ? "|" : "") + " %" + (left ? "-" : "") + colSize + "s |", el));
		first = false;

		return false;
	}

	/**
	 * <p>
	 * Prints the table to the console.
	 * </p>
	 */
	public void print() {
		lastRow = true;
		addCellBorder();
		lastRow = false;

		// print table
		System.out.print(sb.toString());

		sb = temp;
	}

	private void addCellBorder() {

		if (firstRow || lastRow)
			sb.append(corner);
		else
			sb.append("|");

//		int lineLength = numCols * (colSize + 2) + numCols - 1;

		for (int i = 0; i < numCols; i++) {
			char[] sep = new char[colSize + 2];
			Arrays.fill(sep, border_vertical);
			sb.append(sep);
			if (i == numCols - 1) {
				if (!(firstRow || lastRow))
					sb.append('|');

			} else
				sb.append(connector);
		}

		if (firstRow || lastRow)
			sb.append(corner);

		sb.append(NEW_LINE);
	}

	public void fillHeaderRow(Function<Integer, String> callback, boolean leftAligned) {
		String[] s = new String[this.numCols];

		for (int i = 0; i < this.numCols; i++) {
			s[i] = callback.apply(i);
		}

		if (leftAligned)
			addHeader(s, leftAligned);
		else
			addHeader(s);
	}

	/**
	 * <p>
	 * Iterates over each of the cells inserting the returned {@code String} of the
	 * callback provided.
	 * </p>
	 * 
	 * @param callback
	 */
	public void fillRow(Function<Integer, String> callback) {
		String[] s = new String[this.numCols];

		for (int i = 0; i < this.numCols; i++) {
			s[i] = callback.apply(i);
		}

		this.addRow(s);

	}

	@Override
	public String toString() {

		StringBuilder result = new StringBuilder();

		addCellBorder();

		result.append(sb);
		sb = temp;

		return result.toString();
	}

	public static void main(String[] arg) {

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

		table.addRow(new String[] { "The table adjusts the cell height automatically", "Trump", "12" });
		table.print();

	}
}
