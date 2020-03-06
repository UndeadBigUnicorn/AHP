$(document).ready(() => {

    const criteria = ["Ціна", "Запас ходу", "Швидкість", "Зарядки", "Вмісткість", "Комфорт"];
    const criteriaList = $('#criteria-list');

    renderCriteriaList();
    renderTable('criteria-matrix', 'Критерії', criteria);

    $(document).on('click', '.criteria-delete', (e) => {
        let index = $(e.currentTarget).data('index') - 1;
        criteria.splice(index, 1);
        renderCriteriaList();
    });

    $(document).on('change', '.criteria-input', (e) => {
        let self = $(e.currentTarget);
        let index = self.data('index') - 1;
        criteria[index] = self.val();
    });

    $('#add-criteria').click(() => {
        criteria.push('');
        renderCriteriaList()
    });

    function renderCriteriaList() {
        let html = '';
        criteria.forEach((criteria, index) => {
            index++;
            html += `<li>
                    <div class="uk-margin uk-form-horizontal">
                        <label class="uk-form-label" for="criteria${index}">${index}.</label>
                        <div class="uk-form-controls uk-width-1-4@l uk-width-1-2@s">
                            <div class="uk-inline">
                                <a class="uk-form-icon uk-form-icon-flip criteria-delete" href="javascript:void(0);" data-index="${index}" uk-icon="icon: close"></a>
                                <input class="uk-input uk-form-blank criteria-input" id="criteria${index}" type="text" value="${criteria}" data-index="${index}" placeholder="Критерій ${index}">
                            </div>
                        </div>
                    </div>
                </li>`;
        });

        criteriaList.html(html);
    }

    function renderTable(tableId, tableTitle, titles) {
        let table = $(`#${tableId}`);
        let row, cell;

        // append title first row
        row = $( '<tr />' );
        table.append( row );
        cell = $('<th>'+tableTitle+'</th>')
        row.append( cell );
        for (let idx in titles) {
            cell = $('<th>'+titles[idx]+'</th>')
            row.append( cell );
        }

        // other rows
        for(let i=0; i<titles.length; i++){
            row = $( '<tr />' );
            table.append( row );
            cell = $('<th>'+titles[i]+'</th>')
            row.append( cell );

            for(let j=0; j<titles.length; j++){
                cell = $('<td>' +
                    '<input class="uk-input uk-form-width-xsmall" type="text" placeholder="" value="1" max="9" min="0" disabled>'
                    + '</td>')
                row.append( cell );
            }

        }
    }


});