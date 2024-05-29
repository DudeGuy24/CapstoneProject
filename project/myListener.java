import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myListener extends antlrParserBaseListener {

    private File file = null;
    private Map<String, List<List<String>>> ruleMap = new HashMap<>();
    private String ruleRef;
    private List<List<String>> ruleBlockChildrenTexts = new ArrayList<>();
    private List<String> alternativeList = new ArrayList<>();

    /**
     * Sets the file currently being processed.
     *
     * @param file The file to set.
     */
    public void setFile(File file) {
        this.file = file;
    }

    public void printRuleMap() {
        for (Map.Entry<String, List<List<String>>> entry : ruleMap.entrySet()) {
            String key = entry.getKey();
            List<List<String>> value = entry.getValue();
            
            System.out.println("Key: " + key);
            boolean leftRecursionFound = false;
            boolean rightRecursionFound = false;
            List<List<String>> remove = new ArrayList<>();

            for (List<String> innerList : value) {
                System.out.print("  [");
                for (int i = 0; i < innerList.size(); i++) {
                    String s = innerList.get(i);
                    System.out.print( s + " ");
                    if (i == 0 && s.equals(key)) {
                        leftRecursionFound = true;
                        remove.add(innerList);
                    }
                    if (i == innerList.size() - 1 && s.equals(key)) {
                        rightRecursionFound = true;
                    }
                }
                System.out.println("]");
            }

            if (leftRecursionFound) {
                System.out.println("This rule contains left recursion");
            }
            if (rightRecursionFound) {
                System.out.println("This rule contains right recursion");
            }
            
            for (List<String> innerList : remove) {
                innerList.remove(0);
                System.out.print("  [");
                for (int i = 0; i < innerList.size(); i++) {
                    String s = innerList.get(i);
                    System.out.print( s + " ");
                }
                System.out.println("]");

            }

            System.out.println("");
        }
    }

    @Override
    public void enterParserRuleSpec(antlrParser.ParserRuleSpecContext ctx) {
        ruleRef = ctx.RULE_REF().getText();
    }

    @Override
    public void exitParserRuleSpec(antlrParser.ParserRuleSpecContext ctx) {
        ruleMap.put(ruleRef, ruleBlockChildrenTexts);
        ruleBlockChildrenTexts = new ArrayList<>();
    }

    @Override
    public void enterAlternative(antlrParser.AlternativeContext ctx) {
        /*  : elementOptions? element+ | // explicitly allow empty alts */
        if (ctx.element() != null) {
            for (antlrParser.ElementContext el : ctx.element()) {
                String childText = el.getText();
                alternativeList.add(childText);
            }
        }
    }

    @Override
    public void exitAlternative(antlrParser.AlternativeContext ctx) {
        ruleBlockChildrenTexts.add(alternativeList);
        alternativeList = new ArrayList<>();
    }
}