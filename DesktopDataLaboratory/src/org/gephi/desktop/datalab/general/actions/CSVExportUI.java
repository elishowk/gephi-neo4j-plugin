/*
Copyright 2008 WebAtlas
Authors : Mathieu Bastian, Mathieu Jacomy, Julian Bilcke, Eduardo Ramos
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
(at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.desktop.datalab.general.actions;

import java.nio.charset.Charset;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import net.miginfocom.swing.MigLayout;
import org.netbeans.swing.outline.Outline;
import org.openide.util.NbBundle;
import org.openide.util.NbPreferences;

/**
 * UI for selecting CSV export options of a JTable.
 * @author Eduardo Ramos <eduramiba@gmail.com>
 */
public class CSVExportUI extends javax.swing.JPanel {

    private static final String CHARSET_SAVED_PREFERENCES = "CSVExportUI_Charset";
    private static final String SEPARATOR_SAVED_PREFERENCES = "CSVExportUI_Separator";
    private JTable table;
    private JCheckBox[] columnsCheckBoxes;

    /** Creates new form CSVExportUI */
    public CSVExportUI(JTable table) {
        initComponents();
        this.table = table;
        separatorComboBox.addItem(new SeparatorWrapper((','), getMessage("CSVExportUI.comma")));
        separatorComboBox.addItem(new SeparatorWrapper((';'), getMessage("CSVExportUI.semicolon")));
        separatorComboBox.addItem(new SeparatorWrapper(('\t'), getMessage("CSVExportUI.tab")));
        separatorComboBox.addItem(new SeparatorWrapper((' '), getMessage("CSVExportUI.space")));

        separatorComboBox.setSelectedIndex(NbPreferences.forModule(CSVExportUI.class).getInt(SEPARATOR_SAVED_PREFERENCES, 0));//Use saved separator or comma if not saved

        for (String charset : Charset.availableCharsets().keySet()) {
            charsetComboBox.addItem(charset);
        }
        String savedCharset = NbPreferences.forModule(CSVExportUI.class).get(CHARSET_SAVED_PREFERENCES, null);
        if (savedCharset != null) {
            charsetComboBox.setSelectedItem(savedCharset);
        }else{
            charsetComboBox.setSelectedItem(Charset.forName("UTF-8").name());//UTF-8 by default, not system default charset
        }
        refreshColumns();
    }

    public void unSetup(){
        NbPreferences.forModule(CSVExportUI.class).put(CHARSET_SAVED_PREFERENCES, charsetComboBox.getSelectedItem().toString());
        NbPreferences.forModule(CSVExportUI.class).putInt(SEPARATOR_SAVED_PREFERENCES, separatorComboBox.getSelectedIndex());
    }

    private void refreshColumns() {
        boolean outlineTable = table instanceof Outline;
        TableModel model = table.getModel();
        columnsCheckBoxes = new JCheckBox[model.getColumnCount()];
        columnsPanel.removeAll();
        columnsPanel.setLayout(new MigLayout("", "[pref!]"));


        int modelIndex;
        //Show rest of columns:
        for (int i = 0; i < table.getColumnCount(); i++) {
            modelIndex=table.convertColumnIndexToModel(i);
            if(modelIndex==0){
                if(outlineTable){//If outline table, hide nodes tree column from exporting (first column)
                    columnsCheckBoxes[i] = new JCheckBox(model.getColumnName(modelIndex), false);
                    continue;
                }
            }
            columnsCheckBoxes[i] = new JCheckBox(model.getColumnName(modelIndex), true);
            columnsPanel.add(columnsCheckBoxes[i], "wrap");
        }
        columnsPanel.revalidate();
        columnsPanel.repaint();
    }

    public String getDisplayName() {
        return getMessage("CSVExportUI.title");
    }

    public Character getSelectedSeparator() {
        Object item = separatorComboBox.getSelectedItem();
        if (item instanceof SeparatorWrapper) {
            return ((SeparatorWrapper) item).separator;
        } else {
            return item.toString().charAt(0);
        }
    }

    public Integer[] getSelectedColumnsIndexes() {
        ArrayList<Integer> columnsIndexes = new ArrayList<Integer>();
        for (int i = 0; i < columnsCheckBoxes.length; i++) {
            if (columnsCheckBoxes[i].isSelected()) {
                columnsIndexes.add(table.convertColumnIndexToModel(i));
            }
        }
        return columnsIndexes.toArray(new Integer[0]);
    }

    public Charset getSelectedCharset() {
        return Charset.forName(charsetComboBox.getSelectedItem().toString());
    }

    class SeparatorWrapper {

        private Character separator;
        private String displayText;

        public SeparatorWrapper(Character separator) {
            this.separator = separator;
        }

        public SeparatorWrapper(Character separator, String displayText) {
            this.separator = separator;
            this.displayText = displayText;
        }

        @Override
        public String toString() {
            if (displayText != null) {
                return displayText;
            } else {
                return String.valueOf(separator);
            }
        }
    }

    private String getMessage(String resName) {
        return NbBundle.getMessage(CSVExportUI.class, resName);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        separatorLabel = new javax.swing.JLabel();
        separatorComboBox = new javax.swing.JComboBox();
        scroll = new javax.swing.JScrollPane();
        columnsPanel = new javax.swing.JPanel();
        columnsLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        charsetLabel = new javax.swing.JLabel();
        charsetComboBox = new javax.swing.JComboBox();

        separatorLabel.setText(org.openide.util.NbBundle.getMessage(CSVExportUI.class, "CSVExportUI.separatorLabel.text")); // NOI18N

        columnsPanel.setLayout(new java.awt.GridLayout(1, 0));
        scroll.setViewportView(columnsPanel);

        columnsLabel.setText(org.openide.util.NbBundle.getMessage(CSVExportUI.class, "CSVExportUI.columnsLabel.text")); // NOI18N

        descriptionLabel.setText(org.openide.util.NbBundle.getMessage(CSVExportUI.class, "CSVExportUI.descriptionLabel.text")); // NOI18N

        charsetLabel.setText(org.openide.util.NbBundle.getMessage(CSVExportUI.class, "CSVExportUI.charsetLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(descriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(separatorLabel)
                        .addGap(18, 18, 18)
                        .addComponent(separatorComboBox, 0, 77, Short.MAX_VALUE))
                    .addComponent(columnsLabel)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(charsetLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(charsetComboBox, 0, 134, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(descriptionLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(separatorLabel)
                    .addComponent(separatorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(charsetLabel)
                    .addComponent(charsetComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(columnsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox charsetComboBox;
    private javax.swing.JLabel charsetLabel;
    private javax.swing.JLabel columnsLabel;
    private javax.swing.JPanel columnsPanel;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JScrollPane scroll;
    private javax.swing.JComboBox separatorComboBox;
    private javax.swing.JLabel separatorLabel;
    // End of variables declaration//GEN-END:variables
}
