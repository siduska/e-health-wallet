import { Injectable } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';
import { map } from 'rxjs/operators';

@Injectable({ providedIn: 'root' })
export class ReimbursementNotificationService {
  constructor(private rxStompService: RxStompService) {}

  watchClaimUpdates() {
    return this.rxStompService
      .watch('/topic/reimbursement-updates')
      .pipe(map(msg => JSON.parse(msg.body)));
  }
}
