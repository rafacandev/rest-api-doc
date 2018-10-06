function onSnippetLabelClick(label) {
    var snippetContents = label.parentElement.parentElement.getElementsByClassName('snippet-content');
    var targetContent = document.getElementById(label.htmlFor);
    for (var i=0; i < snippetContents.length; i++) {
      var content = snippetContents[i];
      content.classList.remove('active');
    }
    targetContent.classList.add('active');
}