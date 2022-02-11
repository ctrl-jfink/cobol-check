package com.neopragma.cobolcheck.features.testSuiteParser;

import com.neopragma.cobolcheck.services.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class CobolGenerator {
    private final static String SECTION_HEADER_FORMAT = "       SECTION %s.";
    private final static String PARAGRAPH_HEADER_FORMAT = "       %s.";
    private final static String ENDING_PERIOD = "       .";




    static List<String> generateSectionLines(String identifier, List<String> commentLines, List<String> bodyLines){
        List<String> lines = new ArrayList<>();
        lines.add(String.format(SECTION_HEADER_FORMAT, identifier));
        if (commentLines != null)
            lines.addAll(commentLines);
        if (bodyLines != null)
            lines.addAll(bodyLines);
        lines.add(ENDING_PERIOD);
        return lines;
    }

    static List<String> generateParagraphLines(String identifier, List<String> commentLines, List<String> bodyLines){
        List<String> lines = new ArrayList<>();
        lines.add(String.format(PARAGRAPH_HEADER_FORMAT, identifier));
        if (commentLines != null)
            lines.addAll(commentLines);
        if (bodyLines != null)
            lines.addAll(bodyLines);
        lines.add(ENDING_PERIOD);
        return lines;
    }

    static List<String> generateCommentBlock(String... body){
        List<String> commentLines = new ArrayList<>();
        commentLines.add("      *****************************************************************");
        for (String line: body){
            commentLines.add(StringHelper.commentOutLine(line));
        }
        commentLines.add("      *****************************************************************");
        return commentLines;
    }


}