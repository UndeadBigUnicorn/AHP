$(document).ready(() => {

    const criteria = ["Ціна", "Запас ходу", "Швидкість", "Зарядки", "Вмісткість", "Комфорт"];
    const cars = ["Tesla Model 3", "Nissan Leaf", "VW e-Golf", "Hyundai Ioniq"];
    const validValues = ["1", "2", "3", "4", "5", "6", "7", "8", "9"];
    const validInverseValues = ["1", "1/2", "1/3", "1/4", "1/5", "1/6", "1/7", "1/8", "1/9"];

    const criteriaList = $('#criteria-list');
    const matrixContainer = $('#matrix-container');

    renderData();

    $(document).on('click', '.criteria-delete', (e) => {
        let index = $(e.currentTarget).data('index') - 1;
        criteria.splice(index, 1);
        renderData();
    });

    $(document).on('change', '.criteria-input', (e) => {
        let self = $(e.currentTarget);
        let index = self.data('index') - 1;
        criteria[index] = self.val();
        renderData();
    });

    $(document).on('change', '.matrix-cell', (e) => {
        let self = $(e.currentTarget);
        let opposite = self.data('opposite');
        let tableId = self.data('table');
        let value = self.val();

        if (validValues.indexOf(value) === -1 && validInverseValues.indexOf(value) === -1) {
            self.addClass('uk-form-danger');
        } else {
            self.removeClass('uk-form-danger');
            let cell = $(`#${tableId}-cell-${opposite}`);
            let oppositeValue = validInverseValues[validValues.indexOf(value)];

            if (validInverseValues.indexOf(value) !== -1) {
                oppositeValue = validValues[validInverseValues.indexOf(value)];
            }

            cell.val(oppositeValue);
        }

    });

    $('#add-criteria').click(() => {
        criteria.push('');
        renderData()
    });

    function renderData() {
        renderCriteriaList();
        renderTables();
    }

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

    function renderTables() {
        // render criteria
        matrixContainer.html(
        `<div class="uk-container">
            <h2>Оцініть відносні переваги критеріїв</h2>
            <div class="uk-overflow-auto" uk-margin>
                <table id="criteria-matrix" class="uk-table uk-table-responsive">
                </table>
            </div>
        </div>`);
        renderTable('criteria-matrix', 'Критерії', criteria);

        // render alternatives per criteria tables
        for(let i=0; i<criteria.length; i++) {
            matrixContainer.append(
            `<div class="uk-container">
                <h2>Оцініть відносні переваги автомобілів за критерієм "${criteria[i]}"</h2>
                <div class="uk-overflow-auto" uk-margin>
                    <table id="criteria${i}-matrix" class="uk-table uk-table-responsive">
                    </table>
                </div>
            </div>`);
            renderTable(`criteria${i}-matrix`, criteria[i], cars);
        }
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
            cell = $('<th>'+titles[i]+'</th>');
            row.append( cell );

            for(let j=0; j<titles.length; j++){
                let disabled = '';
                let value = '';
                if (titles[i] === titles[j]) {
                    disabled = 'disabled';
                    value = 1;
                }
                let opposite = j + '' + i;
                let cellId = i + '' + j;
                cell = $('<td>' +
                    `<input id="${tableId}-cell-${cellId}" class="uk-input uk-form-width-xsmall matrix-cell" type="text" data-opposite="${opposite}" data-table="${tableId}" placeholder="" value="${value}" max="9" min="0"` + disabled + '>'
                    + '</td>');
                row.append( cell );
            }

        }
    }


});