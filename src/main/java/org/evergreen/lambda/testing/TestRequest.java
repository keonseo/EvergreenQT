package org.evergreen.lambda.testing;

public class TestRequest {
    private String input;

    // Default constructor required
    public TestRequest() { }

    public TestRequest(final String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(final String input) {
        this.input = input;
    }
}
