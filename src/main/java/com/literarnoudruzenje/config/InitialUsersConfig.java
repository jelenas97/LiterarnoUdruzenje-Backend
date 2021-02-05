package com.literarnoudruzenje.config;

import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.camunda.bpm.engine.authorization.Authorization.AUTH_TYPE_GRANT;
import static org.camunda.bpm.engine.authorization.Permissions.ALL;
import static org.camunda.bpm.engine.authorization.Resources.APPLICATION;

@Component
public class InitialUsersConfig {
    private static final String GROUP_TYPE = "WORKFLOW";
    private static final String BOARD_MEMBERS = "boardMembers";
    private static final String BOARD_MEMBER_PASSWORD = "boardMember";
    private static final String EDITORS = "editors";
    private static final String EDITOR_PASSWORD = "editor";
    private static final String TASKLIST = "tasklist";

    @Autowired
    IdentityService identityService;

    @Autowired
    private AuthorizationService authorizationService;

    @PostConstruct
    public void setupUsers() {
        createBoardMembers();
        createEditors();
    }

    private void createBoardMembers() {
        List<User> users = identityService.createUserQuery().userIdIn("boardMember1", "boardMember2", "boardMember3", "boardMember4").list();

        if (users.isEmpty()) {
            User user1 = identityService.newUser("boardMember1");
            user1.setEmail("boardMember1@mail.com");
            user1.setFirstName("BoardMember1");
            user1.setLastName("BoardMember1");
            user1.setPassword(BOARD_MEMBER_PASSWORD);
            identityService.saveUser(user1);

            User user2 = identityService.newUser("boardMember2");
            user2.setEmail("boardMember2@mail.com");
            user2.setFirstName("BoardMember2");
            user2.setLastName("BoardMember2");
            user2.setPassword(BOARD_MEMBER_PASSWORD);
            identityService.saveUser(user2);

            User user3 = identityService.newUser("boardMember3");
            user3.setEmail("boardMember3@mail.com");
            user3.setFirstName("BoardMember3");
            user3.setLastName("BoardMember3");
            user3.setPassword(BOARD_MEMBER_PASSWORD);
            identityService.saveUser(user3);

            User user4 = identityService.newUser("boardMember4");
            user4.setEmail("boardMember4@mail.com");
            user4.setFirstName("BoardMember4");
            user4.setLastName("BoardMember4");
            user4.setPassword(BOARD_MEMBER_PASSWORD);
            identityService.saveUser(user4);

            Group reviewers = identityService.newGroup(BOARD_MEMBERS);
            reviewers.setName("boardMembers");
            reviewers.setType(GROUP_TYPE);
            identityService.saveGroup(reviewers);

            identityService.createMembership("boardMember1", BOARD_MEMBERS);
            identityService.createMembership("boardMember2", BOARD_MEMBERS);
            identityService.createMembership("boardMember3", BOARD_MEMBERS);
            identityService.createMembership("boardMember4", BOARD_MEMBERS);

            //createAuthorizations(BOARD_MEMBERS);
        }
    }

    private void createEditors() {

        List<User> users = identityService.createUserQuery().userIdIn("editor1", "editor2").list();

        if (users.isEmpty()) {
            User user1 = identityService.newUser("editor1");
            user1.setEmail("editor1@mail.com");
            user1.setFirstName("Editor1");
            user1.setLastName("Editor1");
            user1.setPassword(EDITOR_PASSWORD);
            identityService.saveUser(user1);

            User user2 = identityService.newUser("editor2");
            user2.setEmail("editor2@mail.com");
            user2.setFirstName("Editor2");
            user2.setLastName("Editor2");
            user2.setPassword(EDITOR_PASSWORD);
            identityService.saveUser(user2);

            Group editors = identityService.newGroup(EDITORS);
            editors.setName("editors");
            editors.setType(GROUP_TYPE);
            identityService.saveGroup(editors);

            identityService.createMembership("editor1", EDITORS);
            identityService.createMembership("editor2", EDITORS);
        }
    }


    private void createAuthorizations(String groupId) {
        Authorization applicationAuthorization = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        applicationAuthorization.setGroupId(groupId);
        applicationAuthorization.addPermission(ALL);
        applicationAuthorization.setResourceId(TASKLIST);
        applicationAuthorization.setResource(APPLICATION);
        authorizationService.saveAuthorization(applicationAuthorization);

        Authorization processInstanceAuthorization = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        processInstanceAuthorization.setGroupId(groupId);
        processInstanceAuthorization.addPermission(ALL);
        processInstanceAuthorization.setResource(Resources.PROCESS_INSTANCE);
        authorizationService.saveAuthorization(processInstanceAuthorization);

        Authorization processDefinitionAuthorization = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        processDefinitionAuthorization.setGroupId(groupId);
        processDefinitionAuthorization.addPermission(ALL);
        processDefinitionAuthorization.setResource(Resources.PROCESS_DEFINITION);
        processDefinitionAuthorization.setResourceId("textReview");
        authorizationService.saveAuthorization(processDefinitionAuthorization);

        Authorization taskAuthorization = authorizationService.createNewAuthorization(AUTH_TYPE_GRANT);
        taskAuthorization.setGroupId(groupId);
        taskAuthorization.addPermission(ALL);
        taskAuthorization.setResource(Resources.TASK);
        authorizationService.saveAuthorization(taskAuthorization);
    }
}
