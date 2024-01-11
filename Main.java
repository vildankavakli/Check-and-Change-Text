package datastructuresproject2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 * @author Nouredeen Hammad Vildan Kavaklı
 */
public class Main extends javax.swing.JFrame {

    BST dict;
    Highlighter highlighter;

    public Main() {
        initComponents();
        highlighter = textPane.getHighlighter();
    }

    //Replaces the word between the start and end index with the newText string
    public void replaceText(String newText, int start, int end) {
        String oldText = textPane.getText();
        String before = oldText.substring(0, start);
        String after = oldText.substring(end);
        textPane.setText(before + newText + after);
    }

    //Returns the word that the cursor is currently on
    public String getWordAtCursor() {
        int[] coords = getWordCoordsAtCursor();
        return textPane.getText().substring(coords[0], coords[1]);
    }

    //Returns starting and ending index of the word that the cursor is currently on
    public int[] getWordCoordsAtCursor() {
        int pos = textPane.getCaretPosition();
        int start = pos;
        int end = pos;
        String text = textPane.getText();

        //Navigates the starting index until it reaches a whitespace or non-letter character
        while (start > 0 && !Character.isWhitespace(text.charAt(start - 1)) && Character.isLetter(text.charAt(start - 1))) {
            start--;
        }

        //Navigates the ending index until it reaches a whitespace or non-letter character
        while (end < text.length() && !Character.isWhitespace(text.charAt(end)) && Character.isLetter(text.charAt(end))) {
            end++;
        }

        int[] coords = new int[2];
        coords[0] = start;
        coords[1] = end;
        return coords;
    }

    //Adds suggestion menu item to the popup menu
    public void addMenuItem(String item, int[] coords) {
        JMenuItem mi = new JMenuItem(item);

        //Listens to when that menu item is clicked then replaces the wrong word 
        mi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replaceText(item, coords[0], coords[1]);
            }
        });

        //Adds the menu item to the popup menu
        popup.add(mi);
    }

    //Scans the text for words that are not in the dictionary and highlights them red
    void refreshHighlighter() {
        highlighter.removeAllHighlights();
        String text = textPane.getText();
        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.red);

        String[] words = text.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            if (words[i].charAt(words[i].length() - 1) == '.' || words[i].charAt(words[i].length() - 1) == ',' || words[i].charAt(words[i].length() - 1) == '!' || words[i].charAt(words[i].length() - 1) == '?') {
                words[i] = removeLastChar(words[i]);
            }
            if (!dict.search(words[i].toLowerCase())) {
                int p0 = text.indexOf(words[i]);
                int p1 = p0 + words[i].length();
                try {
                    highlighter.addHighlight(p0, p1, painter);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public static String removeLastChar(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }
        return word.substring(0, word.length() - 1);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popup = new javax.swing.JPopupMenu();
        jScrollPane1 = new javax.swing.JScrollPane();
        textPane = new javax.swing.JTextPane();
        browseBtn = new javax.swing.JButton();
        dictLbl = new javax.swing.JLabel();
        fileNameLbl = new javax.swing.JLabel();
        kSlider = new javax.swing.JSlider();
        dictLbl1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        fixPunctuationBtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textPane.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        textPane.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        textPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                textPaneMouseClicked(evt);
            }
        });
        textPane.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                textPaneKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(textPane);

        browseBtn.setText("Browse");
        browseBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseBtnActionPerformed(evt);
            }
        });

        dictLbl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dictLbl.setText("Choose a Dictionary to use this program:");

        fileNameLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        kSlider.setMaximum(10);
        kSlider.setMinimum(1);
        kSlider.setMinorTickSpacing(1);
        kSlider.setPaintLabels(true);
        kSlider.setPaintTicks(true);
        kSlider.setSnapToTicks(true);
        kSlider.setValue(3);

        dictLbl1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        dictLbl1.setText("Number of suggestions");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel1.setText("1    2    3    4    5    6    7    8    9    10");

        fixPunctuationBtn.setText("Fix Punctuation");
        fixPunctuationBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixPunctuationBtnActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Ctrl+Space to autocomplete");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kSlider, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dictLbl1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addComponent(fixPunctuationBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(fileNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(browseBtn))
                                    .addComponent(dictLbl)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dictLbl)
                            .addComponent(dictLbl1))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(kSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(fileNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(browseBtn))))
                    .addComponent(fixPunctuationBtn, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 556, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void browseBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseBtnActionPerformed
        //Sets up a file chooser
        JFileChooser fileChooser = new JFileChooser();

        //Limits selectable file types to TXT files using a FileNameExtensionFilter
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        fileChooser.setFileFilter(filter);

        //Controls the FileChooser
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            //Fills the dict BST from the txt file
            dict = Utility.fileToBST(selectedFile.getAbsolutePath());

            //Enables the JTextPane
            textPane.setEnabled(true);

            //Writes the file name beside the browse button
            fileNameLbl.setText(selectedFile.getName());

            //Uses multithreading to run the red highlighter refresh function every 1 second
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            exec.scheduleAtFixedRate(() -> {
                refreshHighlighter();
            }, 3, 1, TimeUnit.SECONDS);
        }
    }//GEN-LAST:event_browseBtnActionPerformed

    private void textPaneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textPaneKeyPressed
        //Checks for CTRL+Spacebar to initialize autocomplete
        if (evt.isControlDown() && evt.getKeyCode() == 32) {
            int[] coords = getWordCoordsAtCursor();
            String autocompleteWord = BST.returnAutocompleteWord(getWordAtCursor(), dict);

            //Replaces the current word with new word
            //Does nothing if a word isn't found (returns -1)
            if (!autocompleteWord.equals("-1")) {
                replaceText(autocompleteWord, coords[0], coords[1]);
            }
        }
    }//GEN-LAST:event_textPaneKeyPressed

    private void fixPunctuationBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixPunctuationBtnActionPerformed
        String text = textPane.getText();

        //Checks the first letter and changes it to uppercase
        if (!Character.isUpperCase(text.charAt(0))) {
            String first = text.substring(0, 1);
            String others = text.substring(1);
            text = first.toUpperCase() + others;
            textPane.setText(text);

        }

        //Checks for lowercase letters after '.' and turns them to uppercase
        for (int i = 0; i < text.length(); i++) {
            if (i == text.length() - 1 && text.charAt(i) != '.') {
                //If i is 1 less than the length of the text and the character of the text in i is not a period, add a period to the end of the text.
                text += '.';
                textPane.setText(text);

            } else if (i + 2 > text.length()) {
                //If the text ends, I break out of the if else block.
                break;

            } else if ((text.charAt(i) == '.' || text.charAt(i) == '!' || text.charAt(i) == '?')
                    && text.charAt(i + 1) == ' ' && !Character.isUpperCase(text.charAt(i + 2))) {
                //If there is a punctuation mark at the end of the sentence, the next index 
                //is a space, and the next index is lowercase, we capitalize this letter.
                String prevSubstring = text.substring(0, i + 2);
                String nextSubstring = text.substring(i + 3, text.length() - 1);
                text = prevSubstring + Character.toUpperCase(text.charAt(i + 2)) + nextSubstring;
                textPane.setText(text);

            } else if ((text.charAt(i) == '.' || text.charAt(i) == '!' || text.charAt(i) == '?')
                    && text.charAt(i + 1) != ' ' && !Character.isUpperCase(text.charAt(i + 1))) {
                //If there is a punctuation mark at the end of the sentence and the next index 
                //is lowercase, we capitalize this letter.
                String prevSubstring = text.substring(0, i + 1);
                String nextSubstring = text.substring(i + 2, text.length() - 1);
                text = prevSubstring + " " + Character.toUpperCase(text.charAt(i + 1)) + nextSubstring;
                textPane.setText(text);

            }
        }
    }//GEN-LAST:event_fixPunctuationBtnActionPerformed

    private void textPaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_textPaneMouseClicked
        //Checks for right-click on textPane
        if (SwingUtilities.isRightMouseButton(evt)) {
            String selectedWord = getWordAtCursor().toLowerCase();
            int[] wordCoords = getWordCoordsAtCursor();

            //Exits the function if the selected word already exists in the dictionary
            if (dict.search(selectedWord)) {
                return;
            }

            BST suggestionBst = BST.returnSuggestionsTree(selectedWord, dict);
            int sliderVal = kSlider.getValue();
            popup = new JPopupMenu();

            //Loads suggestions into the popup menu according to the value of the kSlider
            for (int i = 0; i < sliderVal; i++) {

                if (suggestionBst.root == null) {
                    break;
                }

                //Gets the best suggestion and adds it to 
                String newItem = "" + suggestionBst.findMin();
                addMenuItem(newItem, wordCoords);
                suggestionBst.deleteMin();
            }

            popup.show(textPane, evt.getX(), evt.getY());

        }
    }//GEN-LAST:event_textPaneMouseClicked

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main f = new Main();
                f.textPane.setEnabled(false);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseBtn;
    private javax.swing.JLabel dictLbl;
    private javax.swing.JLabel dictLbl1;
    private javax.swing.JLabel fileNameLbl;
    private javax.swing.JButton fixPunctuationBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider kSlider;
    private javax.swing.JPopupMenu popup;
    private javax.swing.JTextPane textPane;
    // End of variables declaration//GEN-END:variables
}
