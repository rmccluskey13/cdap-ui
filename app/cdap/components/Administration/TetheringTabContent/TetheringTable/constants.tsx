/*
 * Copyright © 2022 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import React from 'react';
import T from 'i18n-react';
import { StyledIcon } from '../shared.styles';

export const PREFIX = 'features.Administration.Tethering';
export const DESC_COLUMN_TEMPLATE = '50px 200px 5fr 200px 1.5fr 240px 225px';

export const ICONS = {
  active: {
    name: 'icon-check-circle',
    color: 'var(--green)',
    tooltip: 'Active',
  },
  inactive: {
    name: 'icon-times-circle',
    color: 'var(--red)',
    tooltip: 'Inactive',
    pendingReqTooltip: 'Inactive (Could possibly be due to incorrect parameters)',
  },
  header: {
    name: 'icon-circle',
    color: 'var(--grey05)',
  },
  default: {
    name: 'icon-circle',
    color: 'black',
  },
};

export const CONNECTIONS_TABLE_HEADERS = [
  {
    label: <StyledIcon name={ICONS.header.name} color={ICONS.header.color} />,
  },
  {
    property: 'requestTime', // properties may come handy when adding sort by columns to the table, will remove if not needed
    label: T.translate(`${PREFIX}.ColumnHeaders.requestTime`),
  },
  {
    property: 'description',
    label: T.translate(`${PREFIX}.ColumnHeaders.description`),
  },
  {
    property: 'gcp',
    label: T.translate(`${PREFIX}.ColumnHeaders.gcp`),
  },
  {
    property: 'instanceName',
    label: T.translate(`${PREFIX}.ColumnHeaders.instanceName`),
  },
  {
    property: 'region',
    label: T.translate(`${PREFIX}.ColumnHeaders.region`),
  },
  {
    property: 'tetheredNamespace',
    label: T.translate(`${PREFIX}.ColumnHeaders.tetheredNamespace`),
  },
  {
    property: 'cpu',
    label: T.translate(`${PREFIX}.ColumnHeaders.cpu`),
  },
  {
    property: 'memory',
    label: T.translate(`${PREFIX}.ColumnHeaders.memory`),
  },
  {
    label: '',
  },
];