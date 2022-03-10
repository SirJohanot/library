package com.epam.library.command.viewentities;

import com.epam.library.command.Command;
import com.epam.library.command.result.CommandResult;
import com.epam.library.constant.AttributeNameConstants;
import com.epam.library.constant.ParameterNameConstants;
import com.epam.library.entity.Identifiable;
import com.epam.library.exception.ServiceException;
import com.epam.library.pagination.Paginator;

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
        req.setAttribute(AttributeNameConstants.MAX_PAGE, maxPage);

        String pageLine = req.getParameter(ParameterNameConstants.PAGE);
        int page = pageLine == null ? 1 : Integer.parseInt(pageLine);
        if (page <= 0) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        req.setAttribute(AttributeNameConstants.CURRENT_PAGE, page);

        List<T> ordersOfPage = paginator.getElementsOfPage(allEntities, page, elementsPerPage);
        req.setAttribute(listAttributeName, ordersOfPage);

        return CommandResult.forward(pathToForwardTo);
    }

    protected abstract List<T> getEntitiesUsingService(HttpServletRequest req) throws ServiceException;
}
