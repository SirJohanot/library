package com.epam.library.command;

public class CommandResult {

    private final String page;
    private final boolean redirect;

    private CommandResult(String page, boolean redirect) {
        this.page = page;
        this.redirect = redirect;
    }

    public static CommandResult redirect(String page) {
        return new CommandResult(page, true);
    }

    public static CommandResult forward(String page) {
        return new CommandResult(page, false);
    }

    public String getPage() {
        return page;
    }

    public boolean isRedirect() {
        return redirect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommandResult that = (CommandResult) o;

        if (redirect != that.redirect) {
            return false;
        }
        return page != null ? page.equals(that.page) : that.page == null;
    }

    @Override
    public int hashCode() {
        int result = page != null ? page.hashCode() : 0;
        result = 31 * result + (redirect ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandResult{" +
                "page='" + page + '\'' +
                ", redirect=" + redirect +
                '}';
    }
}
