package com.star.common.richclient.layout;


import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.ffxml.swtforms.factories.DefaultComponentFactory;
import net.ffxml.swtforms.factories.FormFactory;
import net.ffxml.swtforms.layout.Border;
import net.ffxml.swtforms.layout.CellConstraints;
import net.ffxml.swtforms.layout.ColumnSpec;
import net.ffxml.swtforms.layout.FormLayout;
import net.ffxml.swtforms.layout.RowSpec;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * A panel builder that provides the capability to quickly build grid based
 * forms. The builder allows for layout to be defined in a way that will be
 * familiar to anyone used to HTML tables and JGoodies Forms. Key features:
 * <ul>
 * <li>unlike HTML, cells automatically span all empty columns to the right.
 * This can be disabled by setting "colSpan=1"</li>
 * <li>support for gap rows and columns. You don't need to keep track of gap
 * rows or columns when specifying row or column spans</li>
 * <li>need only define colSpec and rowSpec when it varies from the default.
 * Save you having to work out column specs before you start laying out the form
 * </li>
 * <li>rows and columns can be aliased with a group ID which save you having to
 * keep track of row or column indexes for grouping. This also makes grouping
 * less fragile when the table layout changes</li>
 * </ul>
 * <strong>Example: </strong> <br>
 * 
 * <pre>
 * TableLayoutBuilder table = new TableLayoutBuilder();
 * table.row().separator(&quot;General 1&quot;).row().cell(new JLabel(&quot;Company&quot;),
 *         &quot;colSpec=right:pref colGrId=labels&quot;).labelGapCol().cell(
 *         new JFormattedTextField()).row().cell(new JLabel(&quot;Contact&quot;)).cell(
 *         new JFormattedTextField()).unrelatedGapRow().separator(&quot;Propeller&quot;)
 *         .row().cell(new JLabel(&quot;PTI [kW]&quot;)).cell(new JFormattedTextField())
 *         .unrelatedGapCol().cell(new JLabel(&quot;Description&quot;),
 *                 &quot;colSpec=right:pref colGrId=labels&quot;).labelGapCol().cell(
 *                 new JScrollPane(new JTextArea()), &quot;rowspan=3&quot;).row().cell(
 *                 new JLabel(&quot;R [mm]&quot;)).cell(new JFormattedTextField()).cell()
 *         .row().cell(new JLabel(&quot;D [mm]&quot;)).cell(new JFormattedTextField())
 *         .cell();
 * 
 * table.getPanel();
 * </pre>
 * 
 * @author oliverh, liuwei
 */
public class TableLayoutBuilder{
    
    public static final String DEFAULT_LABEL_ATTRIBUTES = "colGrId=label colSpec=left:pref";

    public static final String ALIGN = "align";

    public static final String VALIGN = "valign";

    public static final String ROWSPEC = "rowSpec";

    public static final String COLSPEC = "colSpec";

    public static final String ROWSPAN = "rowSpan";

    public static final String COLSPAN = "colSpan";

    public static final String ROWGROUPID = "rowGrId";

    public static final String COLGROUPID = "colGrId";

    private List<RowSpec> rowSpecs = new ArrayList<RowSpec>();

    private List<List<Cell>> rowOccupiers = new ArrayList<List<Cell>>();

    private List<ColumnSpec> columnSpecs = new ArrayList<ColumnSpec>();

    private Map<Integer,ColumnSpec> gapCols = new HashMap<Integer,ColumnSpec>();

    private Map<Integer,RowSpec> gapRows = new HashMap<Integer,RowSpec>();

    private Map<String,Set<Integer>> rowGroups = new HashMap<String,Set<Integer>>();

    private Map<String,Set<Integer>> colGroups = new HashMap<String,Set<Integer>>();

    private int[][] adjustedColGroupIndices;

    private int[][] adjustedRowGroupIndices;

    private Cell lastCC = null;

    private int maxColumns = 0;

    private int currentRow = -1;

    private int currentCol = 0;

    private List<Cell> items = new ArrayList<Cell>();

    private Composite panel;
    
	private Border border;
	
     /**
     * Creates a new TableLayoutBuilder which will perform it's layout
     * in the supplied JPanel. Note that any components that are already 
     * contained by the panel will be removed.
     */
    public TableLayoutBuilder(Composite panel) {
    	if(panel==null)
    		throw new RuntimeException("panel is required"); 
        
        this.panel = panel;
    }

    /**
     * Returns the current row (zero-based) that the builder is putting
     * components in.
     */
    public int getCurrentRow() {
        return currentRow == -1 ? 0 : currentRow;
    }

    /**
     * Returns the current column (zero-based) that the builder is putting
     * components in.
     */
    public int getCurrentCol() {
        return currentCol;
    }

    /**
     * Inserts a new row. No gap row is inserted  before this row.
     */
    public TableLayoutBuilder row() {
        ++currentRow;
        lastCC = null;
        maxColumns = Math.max(maxColumns, currentCol);
        currentCol = 0;
        return this;
    }

    /**
     * Inserts a new row. A gap row with specified RowSpec will be inserted
     * before this row.
     */
    public TableLayoutBuilder row(String gapRowSpec) {
        return row(new RowSpec(gapRowSpec));
    }

    /**
     * Inserts a new row. A gap row with specified RowSpec will be inserted
     * before this row.
     */
    public TableLayoutBuilder row(RowSpec gapRowSpec) {
        row();
        gapRows.put(new Integer(currentRow), gapRowSpec);
        return this;
    }
    
    /**
     * Inserts a new row. A related component gap row will be inserted before
     * this row.
     */
    public TableLayoutBuilder relatedGapRow() {
        return row(FormFactory.RELATED_GAP_ROWSPEC);
    }

    /**
     * Inserts a new row. An unrelated component gap row will be inserted before
     * this row.
     */
    public TableLayoutBuilder unrelatedGapRow() {
        return row(FormFactory.UNRELATED_GAP_ROWSPEC);
    }
    
    /**
     * Inserts an empty cell at the current row/column.
     */
    public TableLayoutBuilder cell() {
        return cell("");
    }

    /**
     * Inserts an empty cell at the current row/column. Attributes may be zero or
     * more of rowSpec, columnSpec, colGrId and rowGrId.
     */
    public TableLayoutBuilder cell(String attributes) {
        cellInternal(null, attributes);
        return this;
    }

    /**
     * Inserts a component at the current row/column.
     */
    public TableLayoutBuilder cell(Control component) {
        return cell(component, "");
    }

    /**
     * Inserts a component at the current row/column. Attributes may be zero or
     * more of rowSpec, columnSpec, colGrId, rowGrId, align and valign.
     */
    public TableLayoutBuilder cell(Control component, String attributes) {
        Cell cc = cellInternal(component, attributes);
        lastCC = cc;
        items.add(cc);
        return this;
    }

    /**
     * Inserts a related component gap column.
     */
    public TableLayoutBuilder gapCol() {
        return gapCol(FormFactory.RELATED_GAP_COLSPEC);
    }

    /**
     * Inserts a gap column with the specified colSpec.
     */
    public TableLayoutBuilder gapCol(String colSpec) {
        return gapCol(new ColumnSpec(colSpec));
    }

    /**
     * Inserts a gap column with the specified colSpec.
     */
    public TableLayoutBuilder gapCol(ColumnSpec colSpec) {
        gapCols.put(new Integer(currentCol), colSpec);
        return this;
    }

    /**
     * Inserts a label component gap column.
     */
    public TableLayoutBuilder labelGapCol() {
        return gapCol(FormFactory.RELATED_GAP_COLSPEC);
    }

    /**
     * Inserts a unrelated component gap column.
     */
    public TableLayoutBuilder unrelatedGapCol() {
        return gapCol(FormFactory.UNRELATED_GAP_COLSPEC);
    }

    /**
     * Inserts a separator with the given label.
     */
    public TableLayoutBuilder separator(String labelKey) {
        return separator(labelKey, "");
    }

    /**
     * Inserts a separator with the given label. Attributes my be zero or more of
     * rowSpec, columnSpec, colGrId, rowGrId, align and valign.
     */
    public TableLayoutBuilder separator(String label, String attributes) {
        Cell cc = cellInternal(createLabeledSeparator(label), attributes);
        lastCC = cc;
        items.add(cc);
        return this;
    }
    
    private Control createLabeledSeparator(String label) {
        return DefaultComponentFactory.getInstance().createSeparator(panel,
        		label, SWT.LEFT);
    }
    
    /**
     * Return true if there is a gap column to the left of the current cell
     */
    public boolean hasGapToLeft() {
        return currentCol == 0 || gapCols.get(new Integer(currentCol)) != null;
    }
    
    /**
     * Return true if there is a gap row above of the current cell
     */
    public boolean hasGapAbove() {
        return currentRow == 0 || gapRows.get(new Integer(currentRow)) != null;
    }

    /**
     * Creates and returns a JPanel with all the given components in it, using
     * the "hints" that were provided to the builder.
     * 
     * @return a new JPanel with the components laid-out in it
     */
    public Composite getPanel() {

        insertMissingSpecs();
        fixColSpans();
        fillInGaps();
        fillPanel();
        return panel;
    }

    private Cell cellInternal(Control component, String attributes) {
        nextCol();
        Map<String,String> attributeMap = getAttributes(attributes);
        RowSpec rowSpec = getRowSpec(getAttribute(ROWSPEC, attributeMap, ""));
        if (rowSpec != null) {
            setRowSpec(currentRow, rowSpec);
        }
        ColumnSpec columnSpec = getColumnSpec(getAttribute(COLSPEC, attributeMap, ""));
        if (columnSpec != null) {
            setColumnSpec(currentCol, columnSpec);
        }
        addRowGroup(getAttribute(ROWGROUPID, attributeMap, null));
        addColGroup(getAttribute(COLGROUPID, attributeMap, null));

        Cell cc = createCell(component, attributeMap);
        currentCol = cc.endCol < cc.startCol ? cc.startCol : cc.endCol;
        markContained(cc);
        return cc;
    }

    private void addRowGroup(String groupId) {
        if (hasText(groupId)) {
            Set<Integer> group = rowGroups.get(groupId);
            if (group == null) {
                group = new HashSet<Integer>();
                rowGroups.put(groupId, group);
            }
            group.add(new Integer(getCurrentRow()));
        }
    }

	private static boolean hasText(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return false;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	private void addColGroup(String groupId) {
        if (hasText(groupId)) {
            Set<Integer> group = colGroups.get(groupId);
            if (group == null) {
                group = new HashSet<Integer>();
                colGroups.put(groupId, group);
            }
            group.add(new Integer(currentCol));
        }
    }

    private void setRowSpec(int row, RowSpec rowSpec) {
        if (row >= rowSpecs.size()) {
            int missingSpecs = row - rowSpecs.size() + 1;
            for (int i = 0; i < missingSpecs; i++) {
                rowSpecs.add(getDefaultRowSpec());
            }
        }
        rowSpecs.set(row, rowSpec);
    }

    private void setColumnSpec(int col, ColumnSpec columnSpec) {
        col = col - 1;
        if (col >= columnSpecs.size()) {
            int missingSpecs = col - columnSpecs.size() + 1;
            for (int i = 0; i < missingSpecs; i++) {
                columnSpecs.add(getDefaultColSpec());
            }
        }
        columnSpecs.set(col, columnSpec);
    }

    private RowSpec getRowSpec(String rowSpec) {
        if (hasText(rowSpec)) {
            return new RowSpec(rowSpec);
        }
        else {
            return null;
        }
    }

    private ColumnSpec getColumnSpec(String columnSpec) {
        if (hasText(columnSpec)) {
            return new ColumnSpec(columnSpec);
        }
        else {
            return null;
        }
    }

    private void nextCol() {
        if (lastCC != null && lastCC.endCol < lastCC.startCol) {
            lastCC.endCol = lastCC.startCol;
            lastCC = null;
        }
        // make sure that the first row has been created
        if (currentRow == -1) {
            row();
        }
        // now find the first unoccupied column
        do {
            ++currentCol;
        }
        while (getOccupier(currentRow, currentCol) != null);
    }

    private Cell getOccupier(int row, int col) {
        List<Cell> occupiers = getOccupiers(row);
        if (col >= occupiers.size()) {
            return null;
        }
        return occupiers.get(col);
    }

    private List<Cell> getOccupiers(int row) {
        if (row >= rowOccupiers.size()) {
            int numMissingRows = (row - rowOccupiers.size()) + 1;
            for (int i = 0; i < numMissingRows; i++) {
                rowOccupiers.add(new ArrayList<Cell>());
            }
        }
        return rowOccupiers.get(row);
    }

    private void markContained(Cell cc) {
        setOccupier(cc, cc.startRow, cc.endRow, cc.startCol, cc.endCol < cc.startCol ? cc.startCol : cc.endCol);
    }

    private void setOccupier(Cell occupier, int startRow, int endRow, int startCol, int endCol) {
        for (int row = startRow; row <= endRow; row++) {
            List<Cell> occupiers = getOccupiers(row);
            if (endCol >= occupiers.size()) {
                int numMissingCols = (endCol - occupiers.size()) + 1;
                for (int i = 0; i < numMissingCols; i++) {
                    occupiers.add(null);
                }
            }
            for (int i = startCol; i <= endCol; i++) {
                occupiers.set(i, occupier);
            }
        }

    }

    private Cell createCell(Control component, Map<String,String> attributes) {
        String align = getAttribute(ALIGN, attributes, "default");
        String valign = getAttribute(VALIGN, attributes, "default");
        int colSpan;
        try {
            colSpan = Integer.parseInt(getAttribute(COLSPAN, attributes, "-1"));
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Attribute 'colspan' must be an integer.");
        }
        int rowSpan;
        try {
            rowSpan = Integer.parseInt(getAttribute(ROWSPAN, attributes, "1"));
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Attribute 'rowspan' must be an integer.");
        }

        return new Cell(component, getCurrentCol(), getCurrentRow(), colSpan, rowSpan, align + "," + valign);
    }

    private void fixColSpans() {
        for (Iterator<Cell> i = items.iterator(); i.hasNext();) {
            Cell cc = i.next();
            if (cc.endCol < cc.startCol) {
                int endCol = cc.startCol;
                while (endCol < maxColumns && getOccupier(cc.startRow, endCol + 1) == null) {
                    ++endCol;
                }
                cc.endCol = endCol;
            }
            markContained(cc);
        }
    }

    private void fillInGaps() {
        List<Integer> adjustedCols = new ArrayList<Integer>();
        int adjustedCol = 0;
        for (int col = 0; col < maxColumns; col++, adjustedCol++) {
            ColumnSpec colSpec = (ColumnSpec)gapCols.get(new Integer(col));
            if (colSpec != null) {
                columnSpecs.add(adjustedCol, colSpec);
                adjustedCol++;
            }
            adjustedCols.add(new Integer(adjustedCol + 1));
        }
        List<Integer> adjustedRows = new ArrayList<Integer>();
        int adjustedRow = 0;
        int numRows = rowSpecs.size();
        for (int row = 0; row < numRows; row++, adjustedRow++) {
            RowSpec rowSpec = (RowSpec)gapRows.get(new Integer(row));
            if (rowSpec != null) {
                rowSpecs.add(adjustedRow, rowSpec);
                adjustedRow++;
            }
            adjustedRows.add(new Integer(adjustedRow));
        }
        for (Iterator i = items.iterator(); i.hasNext();) {
            Cell cc = (Cell)i.next();
            cc.startCol = ((Integer)adjustedCols.get(cc.startCol - 1)).intValue();
            cc.endCol = ((Integer)adjustedCols.get(cc.endCol - 1)).intValue();
            cc.startRow = ((Integer)adjustedRows.get(cc.startRow)).intValue();
            cc.endRow = ((Integer)adjustedRows.get(cc.endRow)).intValue();
        }
        adjustedColGroupIndices = new int[colGroups.size()][];
        int groupsCount = 0;
        for (Iterator i = colGroups.values().iterator(); i.hasNext();) {
            Set group = (Set)i.next();
            adjustedColGroupIndices[groupsCount] = new int[group.size()];
            int groupCount = 0;
            for (Iterator j = group.iterator(); j.hasNext();) {
                adjustedColGroupIndices[groupsCount][groupCount++] = ((Integer)adjustedCols.get(((Integer)j.next())
                        .intValue() - 1)).intValue();
            }
            groupsCount++;
        }

        adjustedRowGroupIndices = new int[rowGroups.size()][];
        groupsCount = 0;
        for (Iterator i = rowGroups.values().iterator(); i.hasNext();) {
            Set group = (Set)i.next();
            adjustedRowGroupIndices[groupsCount] = new int[group.size()];
            int groupCount = 0;
            for (Iterator j = group.iterator(); j.hasNext();) {
                adjustedRowGroupIndices[groupsCount][groupCount++] = ((Integer)adjustedRows.get(((Integer)j.next())
                        .intValue() - 1)).intValue();
            }
            groupsCount++;
        }
    }

    private void fillPanel() {
    	FormLayout layout = createLayout();
    	
        panel.setLayout(layout);
        for (Iterator i = items.iterator(); i.hasNext();) {
            Cell cc = (Cell)i.next();
            cc.getComponent().setParent(panel);
            cc.getComponent().setLayoutData(cc.getCellConstraints());
            layout.addLayoutComponent(cc.getComponent(), cc.getCellConstraints());
        }
    }

    private FormLayout createLayout() {
        ColumnSpec[] columnSpecsArray = (ColumnSpec[])columnSpecs.toArray(new ColumnSpec[columnSpecs.size()]);
        RowSpec[] rowSpecArray = (RowSpec[])rowSpecs.toArray(new RowSpec[rowSpecs.size()]);
        FormLayout layout = new FormLayout(columnSpecsArray, rowSpecArray);
        layout.setColumnGroups(adjustedColGroupIndices);
        layout.setRowGroups(adjustedRowGroupIndices);
        layout.setBorder(border);
        return layout;
    }

    private void insertMissingSpecs() {
        maxColumns = Math.max(maxColumns, currentCol);
        if (columnSpecs.size() < maxColumns) {
            setColumnSpec(maxColumns, getDefaultColSpec());
        }

        if (rowSpecs.size() <= getCurrentRow()) {
            setRowSpec(getCurrentRow(), getDefaultRowSpec());
        }
    }

    private RowSpec getDefaultRowSpec() {
        return FormFactory.DEFAULT_ROWSPEC;
    }

    private ColumnSpec getDefaultColSpec() {
        return new ColumnSpec("default:grow");
    }

    private static final Set<String> allowedAttributes;
    static {
        allowedAttributes = new HashSet<String>();
        allowedAttributes.add(COLSPAN.toLowerCase());
        allowedAttributes.add(ROWSPAN.toLowerCase());
        allowedAttributes.add(COLSPEC.toLowerCase());
        allowedAttributes.add(ROWSPEC.toLowerCase());
        allowedAttributes.add(ALIGN.toLowerCase());
        allowedAttributes.add(VALIGN.toLowerCase());
        allowedAttributes.add(ROWGROUPID.toLowerCase());
        allowedAttributes.add(COLGROUPID.toLowerCase());
    }

    private String getAttribute(String name, Map<String,String> attributeMap, String defaultValue) {
        String value = attributeMap.get(name.toLowerCase());
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    private Map<String,String> getAttributes(String attributes) {
        Map<String,String> attributeMap = new HashMap<String,String>();
        try {
            StreamTokenizer st = new StreamTokenizer(new StringReader(attributes));
            st.resetSyntax();
            st.wordChars(33, 126);
            st.wordChars(128 + 32, 255);
            st.whitespaceChars(0, ' ');
            st.quoteChar('"');
            st.quoteChar('\'');
            st.ordinaryChar('=');

            String name = null;
            boolean needEquals = false;

            while (st.nextToken() != StreamTokenizer.TT_EOF) {
                if (name == null && st.ttype == StreamTokenizer.TT_WORD) {
                    name = st.sval;
                    if (!allowedAttributes.contains(name.toLowerCase())) {
                        throw new IllegalArgumentException("Attribute name '" + name + "' not recognised.");
                    }
                    needEquals = true;
                }
                else if (needEquals && st.ttype == '=') {
                    needEquals = false;
                }
                else if (name != null && (st.ttype == StreamTokenizer.TT_WORD || st.ttype == '\'' | st.ttype == '"')) {
                    attributeMap.put(name.toLowerCase(), st.sval);
                    name = null;
                }
                else {
                    throw new IllegalArgumentException("Expecting '=' but found '" + st.sval + "'");
                }
            }
            if (needEquals || name != null) {
                throw new IllegalArgumentException("Premature end of string. Expecting "
                        + (needEquals ? " '='." : " value for attribute '" + name + "'."));
            }
        }
        catch (IOException e) {
            throw new UnsupportedOperationException("Encountered unexpected IOException. " + e.getMessage());
        }

        return attributeMap;
    }

    private static class Cell {
        private Control component;

        private int startCol;

        private int startRow;

        private int endCol;

        private int endRow;

        private String align;

        public Cell(Control component, int x, int y, int w, int h, String align) {
            this.component = component;
            this.startCol = x;
            this.startRow = y;
            this.endCol = x + w - 1;
            this.endRow = y + h - 1;
            this.align = align;
        }

        public Control getComponent() {
            return component;
        }

        public CellConstraints getCellConstraints() {
            return new CellConstraints().xywh(startCol, startRow + 1, endCol - startCol + 1, endRow - startRow + 1,
                    align);
        }
    }

	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
	}
   
}
