package com.CoworkingSpace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {
    private Admin admin;
    private Workspace workspace1;
    private Workspace workspace2;

    @BeforeEach
    void setUp() {
        admin = new Admin();
        workspace1 = new Workspace(1, "Private Office", 50);
        workspace2 = new Workspace(2, "Meeting Room", 30);
    }

    @Test
    void addWorkspace_ShouldAddWorkspaceToList() {
        admin.addWorkspace(workspace1);
        List<Workspace> workspaces = admin.getWorkspaces();

        assertEquals(1, workspaces.size());
        assertTrue(workspaces.contains(workspace1));
    }


}