import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Task {
  id: number;
  title: string;
  completed: boolean;
  dueDate: string;
  user?: { id: number };
}

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private readonly apiUrl = 'http://localhost:8080/api/task';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Task[]> {
    return this.http.get<Task[]>(`${this.apiUrl}/getAll`);
  }

  create(task: Partial<Task>): Observable<Task> {
    return this.http.post<Task>(`${this.apiUrl}/create`, task);
  }

  delete(id: number): Observable<boolean> {
  return this.http.delete<boolean>(`${this.apiUrl}/delete/${id}`);
}
}