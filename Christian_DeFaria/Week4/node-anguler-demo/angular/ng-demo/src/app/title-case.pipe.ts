import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'titleCase'
})
export class TitleCasePipe implements PipeTransform {

  transform(value: string, args?: any): any {
    if(!value) {
      return null;
    }

    let words: string[] = value.split(' ');

    for(let i =0; i<words.length; i++) {
      let word = words[i];
      if(i > 0 && this.isPreposition(word)) {
        words[i] = word.toLowerCase();
      } else {
        words[i] = this.toTitleCase(word);
      }
    }
    return words.join(' ');
  }

  private isPreposition(word: string): boolean {
    let preopsitions = [
      'of', 'the', 'for', 'over', 'by', 'to', 'in', 'with'
    ];
    
    return preopsitions.includes(word.toLowerCase());
  }

  private toTitleCase(word: string): string {
    return (word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase());
  }

}