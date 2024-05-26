import java.io.File;

public class myListener extends antlrParserBaseListener{
    
    File file = null;

    /**
     * Sets the file currently being processed.
     *
     * @param file The file to set.
     */
    public void setFile(File file){
        this.file = file;
    }
    
    @Override public void enterParserRuleSpec(antlrParser.ParserRuleSpecContext ctx) { 
        System.out.println(ctx.RULE_REF().getText());
    }
}
