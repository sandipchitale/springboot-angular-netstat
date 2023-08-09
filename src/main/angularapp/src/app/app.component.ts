import {AfterViewInit, Component, Inject, OnInit} from '@angular/core';
import {DOCUMENT} from '@angular/common';
import {AppService} from './app.service';
import {NetstatLine} from "./app.netstatline.model";
import {finalize} from "rxjs";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewInit {
  lightTheme = true;

  netstatLines: NetstatLine[] = [];
  selectedNetstatLine: NetstatLine | undefined;
  loading = false;

  constructor(@Inject(DOCUMENT) private document: Document, private apiService: AppService) {}

  ngOnInit(): void {
    this.netstat();
  }

  netstat() {
    this.loading = true;
    this.apiService.netstat()
      .pipe(
        finalize(() => this.loading = false)
      )
      .subscribe((netstatLines: NetstatLine[]) => {
        this.netstatLines = netstatLines;
        if (this.netstatLines.length > 0) {
          this.selectedNetstatLine = this.netstatLines[0];
        }
      });
  }

  ngAfterViewInit(): void {
    this.adjustTheme();
  }

  adjustTheme(event?: any) {
    let theme = 'light-theme';
    if (this.lightTheme) {
      theme = 'light-theme';
    } else {
      theme = 'dark-theme';
    }
    const themeLink = this.document.getElementById('app-theme') as HTMLLinkElement;
    if (themeLink) {
      themeLink.href = theme + '.css';
    }
  }

}
