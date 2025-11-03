import { RxStompService } from '@stomp/ng2-stompjs';
import { eHealthRxStompConfig } from './ehealth-rx-stomp.config';

export function rxStompServiceFactory() {
  const rxStomp = new RxStompService();
  rxStomp.configure(eHealthRxStompConfig);
  rxStomp.activate();
  return rxStomp;
}
