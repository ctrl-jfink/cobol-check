package com.neopragma.cobolcheck.features.testSuiteParser;

import com.neopragma.cobolcheck.services.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class MockGenerator {

    private final String evaluateStartLine = "            EVALUATE UT-TEST-SUITE-NAME ALSO UT-TEST-CASE-NAME";
    private final String whenFormat = "                WHEN %1$s ALSO %2$s";
    private final String performFormat = "                    PERFORM %s";
    private final String whenOtherLine = "           WHEN OTHER";
    private final String endEvaluateLine = "            END-EVALUATE";
    private final String anyKeyword = "ANY";

    /**Generates the lines for SECTIONs based on mocks,
     * for each mock in a given list.
     * @param mocks - The mocks to generate SECTIONs for
     * @return The generated lines
     */
    List<String> generateMockSections(List<Mock> mocks){

        List<String> lines = new ArrayList<>(getMockSectionCommentHeader());
        for (Mock mock : mocks){
            lines.addAll(generateSectionForMock(mock));
            lines.add("");
        }
        return lines;
    }

    /**Generates the lines for 'Evaluate when' to perform the correct generated SECTION
     * for a specific identifier.
     * @param identifier - The identifier of the SECTION, PARAGRAPH etc. that is mocked.
     * @param mocks - All mocks in all tests
     * @return The generated lines
     */
    List<String> generateMockPerformCalls(String identifier, List<Mock> mocks){
        List<String> resultLines = new ArrayList<>();
        List<String> localLines = new ArrayList<>();
        List<String> globalLines = new ArrayList<>();
        resultLines.add(evaluateStartLine);

        for (Mock mock: mocks) {
            if (mock.getIdentifier().equals(identifier)){
                if (mock.getScope() == MockScope.Local){
                    localLines.add(String.format(whenFormat, mock.getTestSuiteName(), mock.getTestCaseName()));
                    localLines.add(String.format(performFormat, mock.getGeneratedMockIdentifier()));
                }
                if (mock.getScope() == MockScope.Global){
                    globalLines.add(String.format(whenFormat, mock.getTestSuiteName(), anyKeyword));
                    globalLines.add(String.format(performFormat, mock.getGeneratedMockIdentifier()));
                }
            }
        }
        //If a local mock exists, that mock should apply
        // Thus we need to have local mocks on top of global mocks
        resultLines.addAll(localLines);
        resultLines.addAll(globalLines);
        resultLines.add(whenOtherLine);

        return resultLines;
    }

    String getEndEvaluateLine() {
        return endEvaluateLine;
    }

    private List<String> getMockSectionCommentHeader(){
        List<String> lines = new ArrayList<>();
        lines.add("      *****************************************************************");
        lines.add(StringHelper.commentOutLine("       Sections called when mocking"));
        lines.add("      *****************************************************************");
        return lines;
    }

    private List<String> generateSectionForMock(Mock mock){
        List<String> lines = new ArrayList<>();
//        lines.add(StringHelper.commentOutLine("       " + mock.getCommentText()));
        lines.add("       " + mock.getGeneratedMockIdentifier() + " SECTION.");
        lines.addAll(mock.getLines());
        lines.add("       .");
        return lines;
    }


}