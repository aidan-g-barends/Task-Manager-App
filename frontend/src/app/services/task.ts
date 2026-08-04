import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Task {
  id: number;
  title: string;
  completed: boolean;
  dueDate: string;
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
}