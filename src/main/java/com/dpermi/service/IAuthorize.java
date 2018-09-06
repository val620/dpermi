package com.dpermi.service;

import com.dpermi.core.PageModel;
import com.dpermi.domain.search.DataPermissionSearch;

/**
 * Created by val620@126.com on 2018/7/23.
 */
public interface IAuthorize {
    PageModel getDataObjects(DataPermissionSearch search);
}
