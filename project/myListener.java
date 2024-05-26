import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myListener extends antlrParserBaseListener{
    
    private File file = null;
    private Map<String, List<String>> ruleMap = new HashMap<>();
    private String ruleRef;
    private List<String> ruleBlockChildrenTexts = new ArrayList<>();
    
    /**
     * Sets the file currently being processed.
     *
     * @param file The file to set.
     */
    public void setFile(File file){
        this.file = file;
    }

    public void printRuleMap(){
        System.out.println(ruleMap);
    }
    
    @Override public void enterParserRuleSpec(antlrParser.ParserRuleSpecContext ctx) { 
        ruleRef = ctx.RULE_REF().getText();
    }

    @Override public void exitParserRuleSpec(antlrParser.ParserRuleSpecContext ctx) { 
        ruleMap.put(ruleRef, ruleBlockChildrenTexts);
        ruleBlockChildrenTexts = new ArrayList<>();
    }

    @Override public void enterAlternative(antlrParser.AlternativeContext ctx) { 
        /*  : elementOptions? element+ | // explicitly allow empty alts */
        if (ctx.element() != null) {
            for (antlrParser.ElementContext el : ctx.element()) {
                String childText = el.getText();
                ruleBlockChildrenTexts.add(childText);
            }
        }
    }
}
