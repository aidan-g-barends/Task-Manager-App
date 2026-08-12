# Task Manager App

A full-stack task management application built with Angular (frontend) and Spring Boot (backend), backed by PostgreSQL.

## Overview

Task Manager App lets you create, track, and organize tasks in a clean, single-page workspace. It's built as a learning/portfolio project, developed feature-by-feature with an emphasis on real debugging discipline — using console and network evidence to diagnose issues rather than guessing.

## Tech Stack

**Frontend**
- Angular (standalone components, signals-based state)
- Reactive Forms
- Angular Router

**Backend**
- Spring Boot
- Spring Data JPA / Hibernate
- PostgreSQL

## Features

- **Task management** — create, view, edit, and delete tasks with a title and due date
- **Mark tasks complete** — toggle completion status directly from the task list
- **All Tasks / Completed views** — filter tasks by completion status via dedicated routes
- **Search** — filter the visible task list by title in real time
- **Notifications** — a dropdown in the top nav surfaces tasks that are due today, due soon, or overdue, with relative labels (e.g. "Due in 2 days", "Overdue by 1 day") and a badge count
- **Task detail & edit views** — view and update individual tasks via their own routes
- **User list** — view registered users in the workspace
- **Client-side routing** — distinct URLs for All Tasks, Completed, Users, and task creation/editing, with active-link highlighting in the sidebar

## Project Status

This is an actively developed project. Current focus areas and known gaps:

- **My Tasks** — planned feature to filter tasks by assignee; requires a proper authentication system (login, current-user context) and task-assignment UI, both not yet built
- **Auth** — no login/session system exists yet; all data is currently unscoped to any particular user

## Project Structure

```
task-manager/
├── frontend/     # Angular application
└── backend/      # Spring Boot application
```

## Development Notes

This project has been built incrementally, with each feature scoped, implemented, and verified against real console/network evidence before moving to the next. Roadmap and known issues are tracked in the project roadmap doc.