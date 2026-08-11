import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';

export interface User {
  id: number;
  name: string;
  email: string;
}

interface PageResponse<T> {
  content: T[];
}

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private readonly apiUrl = 'http://localhost:8080/api/user';

  constructor(private http: HttpClient) {}

  getAll(): Observable<User[]> {
    return this.http.get<PageResponse<User>>(`${this.apiUrl}/getAll`).pipe(
      map((response) => response.content)
    );
  }

  getById(id: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrl}/read/${id}`);
  }

  create(user: Partial<User>): Observable<User> {
    return this.http.post<User>(`${this.apiUrl}/create`, user);
  }

  update(user: User): Observable<User> {
    return this.http.put<User>(`${this.apiUrl}/update`, user);
  }

  delete(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`${this.apiUrl}/delete/${id}`);
  }
}