package com.patiun.library.command.viewentities;

import com.patiun.library.command.Command;
import com.patiun.library.command.result.CommandResult;
import com.patiun.library.constant.AttributeNameConstants;
import com.patiun.library.constant.ParameterNameConstants;
import com.patiun.library.pagination.Paginator;
import com.patiun.library.entity.Identifiable;
import com.patiun.library.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public abstract class AbstractViewPageCommand<T extends Identifiable> implements Command {

    private final String pathToForwardTo;
    private final Paginator<T> paginator;
    private final int elementsPerPage;
    private final String listAttributeName;

    protected AbstractViewPageCommand(Paginator<T> paginator, int elementsPerPage, String listAttributeName, String pathToForwardTo) {
        this.paginator = paginator;
        this.elementsPerPage = elementsPerPage;
        this.listAttributeName = listAttributeName;
        this.pathToForwardTo = pathToForwardTo;
    }

    @Override
    public CommandResult execute(HttpServletRequest req, HttpServletResponse resp) throws ServiceException {
        List<T> allEntities = getEntitiesUsingService(req);

        int maxPage = paginator.getNumberOfPagesNeededToFitElements(allEntities, elementsPerPage);
        maxPage = Math.max(maxPage, 1);
        req.setAttribute(AttributeNameConstants.MAX_PAGE, maxPage);

        String pageLine = req.getParameter(ParameterNameConstants.PAGE);
        int page = pageLine == null ? 1 : Integer.parseInt(pageLine);
        page = Math.max(page, 1);
        page = Math.min(page, maxPage);
        req.setAttribute(AttributeNameConstants.CURRENT_PAGE, page);

        List<T> ordersOfPage = paginator.getElementsOfPage(allEntities, page, elementsPerPage);
        req.setAttribute(listAttributeName, ordersOfPage);

        return CommandResult.forward(pathToForwardTo);
    }

    protected abstract List<T> getEntitiesUsingService(HttpServletRequest req) throws ServiceException;
}
