package org.mybatis.generator2.test_support;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import junit.framework.ComparisonCompactor;

/**
 * This matcher compares the generated string out of a renderer with
 * an expected resource file.
 * 
 * This matcher will read the entire resource file into memory, so it
 * would be best to keep the resource files as small as possible.
 * 
 * This matcher will normalize line endings by converting "\r\n" to "\n".
 * That means that it cannot be used to look for differences in line endings only.
 * 
 * @author Jeff Butler
 *
 */
public class ResourceFileMatcher extends TypeSafeMatcher<String> {

    private String expectedResourcePath;
    private String mismatchMessage;
    
    public ResourceFileMatcher(String expectedResourcePath) {
        this.expectedResourcePath = expectedResourcePath;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("generated code to match code at " + expectedResourcePath);
    }

    @Override
    protected boolean matchesSafely(String item) {
        URL url = ResourceFileMatcher.class.getResource(expectedResourcePath);
        if (url == null) {
            mismatchMessage = expectedResourcePath + " was not found";
            return false;
        }

        try {
            return matches(url, item);
        } catch (IOException | URISyntaxException e) {
            mismatchMessage = e.getMessage();
            return false;
        }
    }

    private boolean matches(URL expectedResource, String actual) throws IOException, URISyntaxException {
        String expected = new String(Files.readAllBytes(Paths.get(expectedResource.toURI())));
        
        String normalizedExpected = expected.replace("\r\n", "\n");
        String normalizedActual = actual.replace("\r\n", "\n");
        if (normalizedExpected.equals(normalizedActual)) {
            return true;
        } else {
            generateMismatchMessage(normalizedExpected, normalizedActual);
            return false;
        }
    }
    
    private void generateMismatchMessage(String expected, String actual) {
        int maxLengthToCompare = Math.min(expected.length(), actual.length());
        int errorPosition = -1;
        for (int i = 0; i < maxLengthToCompare; i++) {
            if (expected.charAt(i) != actual.charAt(i)) {
                errorPosition = i;
                break;
            }
        }

        if (errorPosition == -1) {
            errorPosition = maxLengthToCompare + 1;
        }
        
        ComparisonCompactor cc = new ComparisonCompactor(15,
                exposeLineEndings(expected),
                exposeLineEndings(actual));
        mismatchMessage = cc.compact("first mismatch was near position " + errorPosition + ":");
    }
    
    private String exposeLineEndings(String input) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch (c) {
            case '\n':
                buffer.append("\\n");
                break;
            case '\r':
                buffer.append("\\r");
                break;
            default:
                buffer.append(c);
            }
        }
        
        return buffer.toString();
    }

    @Override
    protected void describeMismatchSafely(String item, Description mismatchDescription) {
        mismatchDescription.appendText(mismatchMessage);
    }

    public static ResourceFileMatcher matchesResourceFile(String targetResourcePath) {
        return new ResourceFileMatcher(targetResourcePath);
    }
}
