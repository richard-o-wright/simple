/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.wrightnz.awt.dnd;

import java.awt.datatransfer.Transferable;
import java.util.List;

/**
 *
 * @author Richard Wright richard@wrightnz.net
 */
public interface IVYTransferInterface {

    Transferable createTransferable();

    void handleImportData(List<?> objects);

    void handleExportDone();

}
