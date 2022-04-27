package com.company.library.specification;

public abstract class AbstractChainedContainsLineSpecification<T> extends AbstractChainedSpecification<T> {

    private final String targetLine;

    public AbstractChainedContainsLineSpecification(String targetLine) {
        this.targetLine = targetLine;
    }

    public AbstractChainedContainsLineSpecification(Specification<T> successor, String targetLine) {
        super(successor);
        this.targetLine = targetLine;
    }

    protected boolean containsTargetLineIgnoreCase(String line) {
        String lowerCaseLine = line.toLowerCase();
        String lowerCaseTargetLine = targetLine.toLowerCase();
        return lowerCaseLine.contains(lowerCaseTargetLine);
    }
}
