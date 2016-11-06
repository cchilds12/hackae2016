package org.projectpost.pages;

import org.eclipse.jetty.http.HttpStatus;
import org.projectpost.data.Database;
import org.projectpost.data.ProjectData;
import org.projectpost.data.UserData;
import org.projectpost.sessions.UserSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProjectPage extends Page {


    @Override
    public void getPage(HttpServletRequest req, HttpServletResponse resp, UserSession session) throws ServletException, IOException {
        String projectID = req.getParameter("pid");
        try {

            System.out.println("HHHUUUH");

            ProjectData pd = Database.getProjectData(projectID);
            if (pd == null) {
                resp.sendRedirect("/");
                return;
            }

            System.out.println(pd.uid);


            Map<String, Object> projectMap = new HashMap<>();
            projectMap.put("title", pd.name);
            UserData ud = Database.getUserData(pd.owner);
            projectMap.put("owner", ud.name);
            projectMap.put("imageData", pd.image);
            projectMap.put("description", pd.description);
            projectMap.put("pid", pd.uid);
            renderTemplate("project.html", projectMap, resp.getWriter());
        } catch (Exception e) {
            sendError(resp, "failed to read template");
        }
    }

    @Override
    public void postPage(HttpServletRequest req, HttpServletResponse resp, UserSession session) throws ServletException, IOException {
        resp.setStatus(HttpStatus.NOT_FOUND_404);
    }
}
