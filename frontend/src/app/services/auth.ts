import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

export interface AuthUser {
  id: number;
  name: string;
  email: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly apiUrl = 'http://localhost:8080/api/auth';
  private readonly storageKey = 'currentUser';

  private readonly currentUser = signal<AuthUser | null>(this.loadFromStorage());
  readonly user = this.currentUser.asReadonly();

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<AuthUser> {
    return this.http
      .post<AuthUser>(`${this.apiUrl}/login`, { email, password })
      .pipe(
        tap((user) => {
          this.currentUser.set(user);
          localStorage.setItem(this.storageKey, JSON.stringify(user));
        })
      );
  }

  logout(): void {
    this.currentUser.set(null);
    localStorage.removeItem(this.storageKey);
  }

  isLoggedIn(): boolean {
    return this.currentUser() !== null;
  }

  private loadFromStorage(): AuthUser | null {
    const raw = localStorage.getItem(this.storageKey);
    if (!raw) return null;
    try {
      return JSON.parse(raw) as AuthUser;
    } catch {
      return null;
    }
  }
}