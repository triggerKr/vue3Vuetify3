import { ICellEditorParams, ICellEditor } from 'ag-grid-community';

export class MultiLineCellEditor implements ICellEditor {
  private eInput!: HTMLTextAreaElement;

  // init 메서드에서 params 받음
  init(params: ICellEditorParams): void {
    this.eInput = document.createElement('textarea');
    this.eInput.value = params.value || '';
    this.eInput.style.width = '100%';
    this.eInput.style.height = '100%';
    this.eInput.style.resize = 'none';
    this.eInput.style.overflow = 'hidden';
    this.eInput.style.lineHeight = '1.4em';

    this.eInput.addEventListener('keydown', (event: KeyboardEvent) => {
      if (event.key === 'Enter') {
        const start = this.eInput.selectionStart;
        const end = this.eInput.selectionEnd;
        this.eInput.value = this.eInput.value.substring(0, start) + "\n" + this.eInput.value.substring(end);
        this.eInput.selectionStart = this.eInput.selectionEnd = start! + 1;
        event.preventDefault();
      }
    });
  }

  getGui(): HTMLElement {
    return this.eInput;
  }

  afterGuiAttached?(): void {
    this.eInput.focus();
  }

  getValue(): any {
    return this.eInput.value;
  }

  isPopup?(): boolean {
    return false;
  }
}
