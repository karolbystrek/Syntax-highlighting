package pl.edu.agh.syntax.highlighting;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class KeywordManager {
    private final Set<String> indentIncreaseKeywords;
    private final Set<String> indentDecreaseKeywords;
    private final Set<String> indentSameLevelKeywords;

    public KeywordManager() {
        this.indentIncreaseKeywords = new HashSet<>();
        this.indentDecreaseKeywords = new HashSet<>();
        this.indentSameLevelKeywords = new HashSet<>();

        initializeDefaultKeywords();
    }

    private void initializeDefaultKeywords() {
        addIndentIncreaseKeyword("if");
        addIndentIncreaseKeyword("while");
        addIndentIncreaseKeyword("for");
        addIndentIncreaseKeyword("function");
        addIndentIncreaseKeyword("class");
        addIndentIncreaseKeyword("constructor");
        addIndentIncreaseKeyword("try");

        addIndentDecreaseKeyword("endif");
        addIndentDecreaseKeyword("endwhile");
        addIndentDecreaseKeyword("endfor");
        addIndentDecreaseKeyword("endfunction");
        addIndentDecreaseKeyword("endclass");
        addIndentDecreaseKeyword("endconstructor");
        addIndentDecreaseKeyword("endtry");

        addIndentSameLevelKeyword("catch");
        addIndentSameLevelKeyword("else");
        addIndentSameLevelKeyword("elsif");
    }

    public void addIndentIncreaseKeyword(String keyword) {
        indentIncreaseKeywords.add(keyword.toLowerCase());
    }

    public void addIndentDecreaseKeyword(String keyword) {
        indentDecreaseKeywords.add(keyword.toLowerCase());
    }

    public void addIndentSameLevelKeyword(String keyword) {
        indentSameLevelKeywords.add(keyword.toLowerCase());
    }

    public boolean isIndentIncreaseKeyword(String keyword) {
        return indentIncreaseKeywords.contains(keyword.toLowerCase());
    }

    public boolean isIndentDecreaseKeyword(String keyword) {
        return indentDecreaseKeywords.contains(keyword.toLowerCase());
    }

    boolean isIndentSameLevelKeyword(String keyword) {
        return indentSameLevelKeywords.contains(keyword.toLowerCase());
    }

    public Set<String> getIndentIncreaseKeywords() {
        return Collections.unmodifiableSet(indentIncreaseKeywords);
    }

    public Set<String> getIndentDecreaseKeywords() {
        return Collections.unmodifiableSet(indentDecreaseKeywords);
    }

    public Set<String> getIndentSameLevelKeywords() {
        return Collections.unmodifiableSet(indentSameLevelKeywords);
    }
}
