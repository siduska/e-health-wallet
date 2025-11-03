import { RxStompConfig } from '@stomp/rx-stomp';
import {environment} from '../../environments/environment';

export const eHealthRxStompConfig: RxStompConfig = {
  brokerURL: environment.wsUrl,
  reconnectDelay: 5000,
  heartbeatIncoming: 0,
  heartbeatOutgoing: 20000,
  debug: (msg: string): void => {
    console.log(new Date(), msg);
  },
};
