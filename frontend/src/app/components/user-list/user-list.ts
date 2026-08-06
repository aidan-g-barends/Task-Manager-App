import { Component, OnInit, signal } from '@angular/core';import { User, UserService } from '../../services/user';

@Component({
  selector: 'app-user-list',
  imports: [],
  templateUrl: './user-list.html',
  styleUrl: './user-list.css',
})
export class UserList implements OnInit {

  protected readonly users = signal<User[]>([]);

  constructor(private userService: UserService) {}
  ngOnInit(): void {
    this.userService.getAll().subscribe((data) => {
      this.users.set(data);
    });
  }
}
