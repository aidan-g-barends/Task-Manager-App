import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

export interface AuthUser {
  id: number;
  name: string;
  email: string;
  role: string;
}

interface LoginResponse extends AuthUser {
  token: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly apiUrl = 'http://localhost:8080/api/auth';
  private readonly userStorageKey = 'currentUser';
  private readonly tokenStorageKey = 'authToken';

  private readonly currentUser = signal<AuthUser | null>(this.loadUserFromStorage());
  readonly user = this.currentUser.asReadonly();

  constructor(private http: HttpClient) {}

  login(email: string, password: string): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${this.apiUrl}/login`, { email, password })
      .pipe(
        tap((response) => {
          const { token, ...user } = response;
          this.currentUser.set(user);
          localStorage.setItem(this.userStorageKey, JSON.stringify(user));
          localStorage.setItem(this.tokenStorageKey, token);
        })
      );
  }

  logout(): void {
    this.currentUser.set(null);
    localStorage.removeItem(this.userStorageKey);
    localStorage.removeItem(this.tokenStorageKey);
  }

  isLoggedIn(): boolean {
    return this.currentUser() !== null;
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenStorageKey);
  }

  private loadUserFromStorage(): AuthUser | null {
    const raw = localStorage.getItem(this.userStorageKey);
    if (!raw) return null;
    try {
      return JSON.parse(raw) as AuthUser;
    } catch {
      return null;
    }
  }
}