import {
  ApplicationConfig,
  provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection
} from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';
import { RxStompService } from '@stomp/ng2-stompjs';
import { rxStompServiceFactory } from './services/rx-stomp-service-factory';
import { provideAnimations } from '@angular/platform-browser/animations'; // ok for now, waiting for updates
import { provideToastr } from 'ngx-toastr';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(),
    provideAnimations(),
    provideToastr(),

    {
      provide: RxStompService,
      useFactory: rxStompServiceFactory
    }
  ]
};
