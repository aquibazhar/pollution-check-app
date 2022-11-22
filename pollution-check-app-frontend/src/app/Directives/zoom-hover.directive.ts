import { Directive, ElementRef, Renderer2, HostListener } from '@angular/core'

@Directive({
  selector: '[appZoomHover]',
})
export class ZoomHoverDirective {
  @HostListener('mouseover')
  onMouseOver() {
    this.r.setStyle(this.el.nativeElement, 'transform', 'scale(1.05)')
    this.r.setStyle(this.el.nativeElement, 'transition', 'transform 0.5s')
    this.r.setStyle(this.el.nativeElement, 'z-index', '1')
  }

  @HostListener('mouseout')
  onMouseOut() {
    this.r.setStyle(this.el.nativeElement, 'transform', 'scale(1)')
    this.r.setStyle(this.el.nativeElement, 'transition', 'transform 0.5s')
    this.r.setStyle(this.el.nativeElement, 'z-index', '0')
  }
  constructor(private el: ElementRef, private r: Renderer2) {}
}
