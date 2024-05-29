import java.io.*;
import java.util.*;

public class myListener extends antlrParserBaseListener {

    private File file = null;
    private PrintWriter writer = null;
    private Map<String, List<List<String>>> ruleMap = new HashMap<>();
    private String ruleRef;
    private List<List<String>> ruleBlockChildrenTexts = new ArrayList<>();
    private List<String> alternativeList = new ArrayList<>();

    public void setFile(File file) {
        this.file = file; 
        String newFileName = file.getPath().replace(".txt", "_results.txt"); 
        try {
            this.writer = new PrintWriter(new FileWriter(newFileName, true)); 
        } catch (IOException e) {
            System.err.println("Failed to open file for writing: " + e.getMessage());
        }
    }
    

    public void printRuleMap() {
        for (Map.Entry<String, List<List<String>>> entry : ruleMap.entrySet()) {
            String key = entry.getKey();
            List<List<String>> value = entry.getValue();
            
            writer.print(key + " : ");
            boolean leftRecursionFound = false;
            boolean rightRecursionFound = false;
            boolean switc = false; 

            List<String> firstList = value.get(0);
            for (int i = 0; i < firstList.size(); i++) {
                String s = firstList.get(i);
                if (i == 0 && s.equals(key)) {
                    leftRecursionFound = true;
                    switc = true;
                    System.out.println("Rule " + key + " contained left recursion at alternative " + i + " with element '" + s + "'");
                } 
                else if (i == firstList.size() - 1 && s.equals(key)) {
                    rightRecursionFound = true;
                    switc = true;
                    System.out.println("Rule " + key + " contained left recursion at alternative " + i + " with element '" + s + "'");
                    writer.print("* ");
                } 

                else {
                    writer.print(s + " ");
                }
            }
            writer.println();


            for (int index = 1; index < value.size(); index++) {
                List<String> innerList = value.get(index);
                writer.print(" | ");
                leftRecursionFound = false;
                rightRecursionFound = false;
                for (int i = 0; i < innerList.size(); i++) {
                    String s = innerList.get(i);
                    if (i == 0 && s.equals(key)) {
                        leftRecursionFound = true;
                        switc = true;
                        System.out.println("Rule " + key + " contained left recursion at alternative " + index + " with element '" + s + "'");
                    } 
                    else if (i == innerList.size() - 1 && s.equals(key)) {
                        rightRecursionFound = true;
                        switc = true;
                        System.out.println("Rule " + key + " contained left recursion at alternative " + index + " with element '" + s + "'");
                        if(!leftRecursionFound){
                            writer.print("* ");
                        }
                    } 

                    else {
                        writer.print(s + " ");
                    }
                }
                writer.println();
            }

            if (switc){
                writer.println(" | ");
            }
            writer.println(" ; ");
            writer.println();
        }
        writer.flush();
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