import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class myListener extends antlrParserBaseListener{
    
    File file = null;
    Map<String, List<String>> ruleMap = new HashMap<>();
    

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
        String ruleRef = ctx.RULE_REF().getText();
        List<String> ruleBlockChildrenTexts = new ArrayList<>();

        for (int i = 0; i < ctx.ruleBlock().getChildCount(); i++) {
            String childText = ctx.ruleBlock().getChild(i).getText();
            ruleBlockChildrenTexts.add(childText);
        }

        ruleMap.put(ruleRef, ruleBlockChildrenTexts);
    }

}
