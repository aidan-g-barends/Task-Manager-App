import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';

export interface Task {
  id: number;
  title: string;
  completed: boolean;
  dueDate: string;
  user?: { id: number; name?: string; email?: string } | null;
}

interface PageResponse<T> {
  content: T[];
}

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private readonly apiUrl = 'http://localhost:8080/api/task';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Task[]> {
    return this.http.get<PageResponse<Task>>(`${this.apiUrl}/getAll`).pipe(
      map((response) => response.content)
    );
  }

  create(task: Partial<Task>): Observable<Task> {
    return this.http.post<Task>(`${this.apiUrl}/create`, task);
  }

  update(task: Task): Observable<Task> {
  return this.http.put<Task>(`${this.apiUrl}/update`, task);
}

  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.apiUrl}/delete/${id}`);
  }

  getById(id: number): Observable<Task> {
  return this.http.get<Task>(`${this.apiUrl}/read/${id}`);
}
}